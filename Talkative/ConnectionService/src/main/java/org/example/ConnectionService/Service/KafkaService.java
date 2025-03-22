package org.example.ConnectionService.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.retry.annotation.Retry;
import org.example.ConnectionService.DTO.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

import java.io.DataInput;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class KafkaService {

    @Autowired
    private ReactiveRedisTemplate<String,Messages> redisMessageTemplate;

    private final KafkaSender<String,String> kafkaSender;

    public KafkaService()
    {
        Map<String, Object> kafkaconfigs = new HashMap<>();
        kafkaconfigs.put("bootstrap.servers","localhost:9092");
        kafkaconfigs.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        kafkaconfigs.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        this.kafkaSender = KafkaSender.create(SenderOptions.create(kafkaconfigs));
    }

    @Retry(name = "sendMessage", fallbackMethod = "storeMessageInCache")
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

   private Mono<Void> storeMessageInCache(String topic, String key, Messages message, Exception ex) {
        try {
            String cacheKey = "failed_message:" + topic +","+ key;

            // Store the message in Redis with an expiration time (e.g., 10 minutes)
            return redisMessageTemplate.opsForValue().set(cacheKey, message ,Duration.ofMinutes(10))
                    .doOnSuccess(done -> System.err.println("Stored failed message in cache: " + cacheKey))
                    .then();
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

  @Scheduled(fixedRate = 600) // Run every 60 seconds
public void retryFailedMessages() {

      System.out.println("retrying .... ");
    ObjectMapper objectMapper = new ObjectMapper();
    redisMessageTemplate.keys("failed_message:*")
            .flatMap(key -> redisMessageTemplate.opsForValue().get(key)
                    .flatMap(json -> {
                        try {
                            Messages message = objectMapper.readValue((DataInput) json, Messages.class); // Fix: no need for DataInput

                            String[] parts = key.split(":");

                            if (parts.length > 1) {
                                // Extract the part after ":" (jhgfdg,vsjvfv)
                                String[] topicAndKey = parts[1].split(",");

                                if (topicAndKey.length == 2) {
                                    // Extract topic and key
                                    String topic = topicAndKey[0];
                                    String rest = topicAndKey[1];

                                    // Retry the message sending
                                    return sendMessage(topic, rest, message)
                                            .then(redisMessageTemplate.delete(key)); // Remove from cache after retry
                                } else {
                                    return Mono.error(new RuntimeException("Invalid format in cache key: " + key));
                                }
                            }
                            return Mono.error(new RuntimeException("Invalid key format: " + key));
                        } catch (JsonProcessingException e) {
                            return Mono.error(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }))
            .subscribe();
}

}
