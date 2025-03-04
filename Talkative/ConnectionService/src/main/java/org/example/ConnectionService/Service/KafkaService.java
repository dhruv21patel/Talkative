package org.example.ConnectionService.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ConnectionService.DTO.Messages;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaService {

    private final KafkaSender<String,String> kafkaSender;

    public KafkaService()
    {
        Map<String, Object> kafkaconfigs = new HashMap<>();
        kafkaconfigs.put("bootstrap.servers","localhost:9092");
        kafkaconfigs.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        kafkaconfigs.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        this.kafkaSender = KafkaSender.create(SenderOptions.create(kafkaconfigs));
    }

    public Mono<Void> sendMessage(String topic,String Key, Messages message) throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        String jsonMessage = m.writeValueAsString(message);
        SenderRecord<String, String, String> record = SenderRecord.create(topic, null, null, Key, jsonMessage, null);

         kafkaSender.send(Mono.just(record))
                .doOnNext(result -> System.out.println("Message sent to topic: " + result.recordMetadata().topic()))
                .doOnError(e -> System.err.println("Error sending message: " + e.getMessage()))
                .subscribe();
        return null;
    }
}
