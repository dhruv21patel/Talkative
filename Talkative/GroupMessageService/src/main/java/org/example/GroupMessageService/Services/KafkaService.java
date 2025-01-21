package org.example.GroupMessageService.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.example.GroupMessageService.DTO.SourceMessage;
import org.example.GroupMessageService.Models.Chats;
import org.example.GroupMessageService.Models.Messages;
import org.example.GroupMessageService.Repository.ChatRepo;
import org.example.GroupMessageService.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KafkaService {

    private final ObjectMapper objectMapper;
    private final MessageRepo messageRepo;
    private final String server;
    private final String groupid;
    private final String offset;
    private final String keydeserializer;
    private final String valuedeserializer;

    @Autowired
    public KafkaService(
            MessageRepo messageRepo, ChatRepo chatRepo, MemberRepo memberRepo,
            @Value("${spring.kafka.consumer.bootstrap-servers}") String server,
            @Value("${spring.kafka.consumer.group-id}") String groupid,
            @Value("${spring.kafka.consumer.auto-offset-reset}") String offset,
            @Value("${spring.kafka.consumer.key-deserializer}") String keydeserializer,
            @Value("${spring.kafka.consumer.value-deserializer}") String valuedeserializer
    ) {
        this.messageRepo = messageRepo;
        this.server = server;
        this.groupid = groupid;
        this.offset = offset;
        this.keydeserializer = keydeserializer;
        this.valuedeserializer = valuedeserializer;
        this.objectMapper = new ObjectMapper();
    }

    @PostConstruct
    public void startKafkaReceiver() {
        Map<String, Object> rconfig = new HashMap<>();
        rconfig.put("bootstrap.servers", server);
        rconfig.put("key.deserializer", keydeserializer);
        rconfig.put("value.deserializer", valuedeserializer);
        rconfig.put("group.id", groupid);
        rconfig.put("enable.auto.commit", false);
        rconfig.put("auto.offset.reset", offset);
        rconfig.put("session.timeout.ms", 30000);

        ReceiverOptions<String, String> receiverOptions = ReceiverOptions.<String, String>create(rconfig)
                .commitRetryInterval(Duration.ZERO)
                .commitBatchSize(0)
                .subscription(Collections.singleton("GroupMessages"));

        KafkaReceiver.create(receiverOptions)
            .receiveAtmostOnce()
            .subscribe(message -> {
                try {
                    SourceMessage m = objectMapper.readValue(message.value(), SourceMessage.class);
                    Messages newMessage = Messages.builder()
                        .ChatID(m.getChatid())
                        .SenderID(m.getSender_id())
                        .Message(m.getMessage())
                        .Seen(false)
                        .build();

                    messageRepo.save(newMessage)
                        .flatMap(savedMessage -> messageRepo.findById(savedMessage.getMessageID()))
                        .doOnSuccess(saved -> System.out.println("Saved Message: " + saved))
                        .doOnError(Throwable::printStackTrace)
                        .subscribe();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
    }
}


