package org.example.IndividualService.Controller;

import org.example.IndividualService.DTO.ResponseMessage;
import org.example.IndividualService.Services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class ReceiverService {

    @Autowired
    ChatService chatService;

    @GetMapping(value = "/{Chatid}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<List<ResponseMessage>> Messages(@PathVariable  String Chatid)
    {

        return chatService.getmessages(Chatid).map(M -> ResponseMessage
                .builder()
                .Message(M.getMessage())
                .seen(M.getSeen())
                .sender_id(M.getSenderID())
                .Chatname(M.getChatID())
                .sendtime(M.getSendTime())
                .build()
        ).doOnError(E-> System.out.println(E)).collectList();
    }

}
