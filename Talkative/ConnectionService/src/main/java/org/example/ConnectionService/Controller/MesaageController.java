package org.example.ConnectionService.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.ConnectionService.DTO.Messages;
import org.example.ConnectionService.DTO.RequestConnection;
import org.example.ConnectionService.Service.MessageService;
import org.example.ConnectionService.Service.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequestMapping("/Chat")
public class MesaageController {

    @Autowired
    MessageService messageService;

    @Autowired
    RoomMapper roomMapper;

    @PostMapping("/{Roomid}")
    public Mono<String> SendMessage(@PathVariable String Roomid , @RequestBody Messages messages)
    {
        RequestConnection request = RequestConnection.builder().sender_id(messages.getSender_id()).receiver_id(messages.getReceiver_id()).IsGroup(messages.getIsGroup()).Groupname(messages.getGroupname()).build();
        return roomMapper.GenerateChatId(request).flatMap(Id -> {
            messages.setChatid(Id);
            try {
                return messageService.SaveMessage(Id,messages);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
