package org.example.ConnectionService.Service;

import org.example.ConnectionService.Constants.AllConstants;
import org.example.ConnectionService.DTO.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MessageService {


    @Autowired
    KafkaService kafkaService;


    public Mono<String> SaveMessage(String Key, Messages message)
    {
        kafkaService.sendMessage(AllConstants.Topic,Key,message.toString());
        return Mono.empty();
    }
}
