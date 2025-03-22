package org.example.ConnectionService.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.ConnectionService.DTO.Messages;
import org.example.ConnectionService.DTO.RequestConnection;
import org.example.ConnectionService.Service.MessageService;
import org.example.ConnectionService.Service.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;


@Controller
public class WebSocketController {

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Autowired
    RoomMapper roomMapper;

    @Autowired
    MessageService messageService;

    @MessageMapping("/Msg/{RoomId}")
    public Mono<Void> sendMessage(@Payload Messages messages, @DestinationVariable("RoomId") String RoomId) {

         RequestConnection request = RequestConnection.builder().sender_id(messages.getSender_id()).receiver_id(messages.getReceiver_id()).IsGroup(messages.getIsGroup()).Groupname(messages.getGroupname()).build();
         return roomMapper.GenerateChatId(request).flatMap(Id -> {
                messages.setChatid(Id);
                try {
                    System.out.println(messages);
                    messagingTemplate.convertAndSend("/topic/Msg/" + RoomId, messages);
                    return messageService.SaveMessage(Id,messages);

                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
        }).then();

    }

}
