package org.example.InidividualMessageService.Services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaService {
    private final Flux<String> messages;

    public KafkaService()
    {
        Map<String,Object> rconfig = new HashMap<>();
        rconfig.put("bootstrap.servers","localhost:9092");
        rconfig.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        rconfig.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        rconfig.put("group.id", "reactive-group");
        rconfig.put("auto.offset.reset", "earliest");

        ReceiverOptions<String, String> receiverOptions = ReceiverOptions.<String,String>create(rconfig)
            .subscription(Collections.singleton("Messages"));

        this.messages = KafkaReceiver.create(receiverOptions)
            .receive()
            .map(record -> {
                record.receiverOffset().acknowledge(); // Acknowledge the message
                return record.value();
            });

    }

    public Flux<String> consumeMessages() {
        return messages;
    }
}
