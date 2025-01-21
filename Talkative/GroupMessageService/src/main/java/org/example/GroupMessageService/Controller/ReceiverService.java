package org.example.GroupMessageService.Controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ReceiverService {

//    @Autowired
//    KafkaService kafkaService;

    @GetMapping(value = "/",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getMessages()
    {
        return null;
    }
}
