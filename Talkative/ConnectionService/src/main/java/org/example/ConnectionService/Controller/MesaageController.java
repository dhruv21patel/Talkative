package org.example.ConnectionService.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.protobuf.Descriptors;
import org.example.ConnectionService.DTO.Messages;
import org.example.ConnectionService.DTO.RequestConnection;
import org.example.ConnectionService.ResponseMessage;
import org.example.ConnectionService.Service.GrpcService;
import org.example.ConnectionService.Service.MessageService;
import org.example.ConnectionService.Service.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.management.Descriptor;
import java.util.Objects;

@RestController
@RequestMapping("/Chat")
public class MesaageController {

    @Autowired
    MessageService messageService;

    @Autowired
    RoomMapper roomMapper;

    @Autowired
    GrpcService grpcService;

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

    @GetMapping("/{chatid}")
    public Flux<ResponseMessage> getMessages(@PathVariable String chatid, @RequestBody RequestConnection requestConnection )
    {
        if(requestConnection.getIsGroup())
        {
            return grpcService.getIndivi(chatid);
        }

        return grpcService.getGroup(chatid);
    }
}
