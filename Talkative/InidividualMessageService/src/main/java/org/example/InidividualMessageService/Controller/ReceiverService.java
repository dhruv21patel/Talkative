package org.example.InidividualMessageService.Controller;

import org.example.InidividualMessageService.Models.Messages;
import org.example.InidividualMessageService.Services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ReceiverService {

    @Autowired
    ChatService chatService;

    @GetMapping(value = "/{Chatid}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Messages> Messages(@PathVariable  String Chatid)
    {
        return chatService.getmessages(Chatid);
    }

}
