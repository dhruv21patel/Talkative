package org.example.IndividualService.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.IndividualService.DTO.SourceMessage;
import org.example.IndividualService.Models.Messages;
import org.example.IndividualService.Repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaService {

    private final ObjectMapper objectMapper;

    @Autowired
    MessageRepo messageRepo;

    public KafkaService()
    {
        this.objectMapper = new ObjectMapper();
        Map<String,Object> rconfig = new HashMap<>();
        rconfig.put("bootstrap.servers","localhost:9092");
        rconfig.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        rconfig.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        rconfig.put("group.id", "reactive-group");
        rconfig.put("enable.auto.commit", false); // Disable auto-commit
        rconfig.put("auto.offset.reset", "earliest");
        rconfig.put("session.timeout.ms", 30000);
//        rconfig.put("max.poll.interval.ms", 300000);
//        rconfig.put("max.poll.records", 50);

        ReceiverOptions<String, String> receiverOptions = ReceiverOptions.<String,String>create(rconfig)
                .commitRetryInterval(Duration.ZERO)
                .commitBatchSize(0)
                .subscription(Collections.singleton("Messages"));

        KafkaReceiver.create(receiverOptions)
            .receiveAtmostOnce()
            .subscribe(Message -> {
                try {
                    SourceMessage m = objectMapper.readValue(Message.value(),SourceMessage.class);
                    Messages newMessage = Messages.builder()
                            .MessageID(null)  // Ensure it's a new UUID
                            .ChatID(m.getChatid())
                            .SenderID(m.getSender_id())
                            .Message(m.getMessage())
                            .Seen(false)
                            .SendTime(Timestamp.valueOf(LocalDateTime.now()))  // ✅ Ensure it's set
                            .build();

                    messageRepo.save(newMessage)
//                        .flatMap(savedMessage -> messageRepo.findById(savedMessage.getMessageID()))  // ✅ Fetch auto-generated fields
                        .doOnSuccess(saved -> System.out.println("Saved Message: " + saved))
                        .doOnError(Throwable::printStackTrace)  // ✅ Debugging logs
                        .subscribe();
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });
    }


}
