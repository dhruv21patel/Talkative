package org.example.ConnectionService.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.ConnectionService.Constants.AllConstants;
import org.example.ConnectionService.DTO.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MessageService {


    @Autowired
    KafkaService kafkaService;


    public Mono<String> SaveMessage(String Key, Messages message) throws JsonProcessingException {
        String Topic;
        if(message.getIsGroup() && message.getGroupname() != null)
        {
            Topic = AllConstants.GroupTopic;
        }
        else {
            Topic = AllConstants.Topic;
        }
        kafkaService.sendMessage(Topic,Key,message);
        return Mono.just(message.getMessage());
    }
}
