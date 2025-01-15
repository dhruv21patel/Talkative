package org.example.InidividualMessageService.Controller;

import org.example.InidividualMessageService.Services.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ReceiverService {

    @Autowired
    KafkaService kafkaService;

    @GetMapping(value = "/",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getMessages()
    {
        return kafkaService.consumeMessages();
    }
}
