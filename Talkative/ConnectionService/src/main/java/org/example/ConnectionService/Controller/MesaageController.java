package org.example.ConnectionService.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.protobuf.Timestamp;
import org.example.ConnectionService.DTO.ResponseMessageDTO;
import org.example.ConnectionService.Exception.UnknownRoom;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

    @Autowired
    SimpMessagingTemplate messagingTemplate;

   @PostMapping("/{Roomid}")
public Mono<ResponseEntity<String>> SendMessage(@PathVariable String Roomid, @RequestBody Messages messages) {
    RequestConnection request = RequestConnection.builder()
            .sender_id(messages.getSender_id())
            .receiver_id(messages.getReceiver_id())
            .IsGroup(messages.getIsGroup())
            .Groupname(messages.getGroupname())
            .build();

    return roomMapper.GenerateChatId(request)
            .flatMap(Id -> {
                // Set chat ID to message
                messages.setChatid(Id);

                // Validate Room ID
                return roomMapper.getRoomid(Id)
                        .flatMap(v -> {
                            if (Objects.equals(v.getRoomid(), Roomid)) {
                                // Proceed if Roomid is valid
                                try {
                                    // Broadcast the message to the specific room
                                    messagingTemplate.convertAndSend("/topic/" + Roomid, messages.getMessage());
                                    return messageService.SaveMessage(Id, messages)
                                            .then(Mono.just(ResponseEntity.ok("Message Sent")));
                                } catch (JsonProcessingException e) {
                                    return Mono.error(new RuntimeException("Error broadcasting message", e));
                                }
                            } else {
                                // Return error with custom status code if Roomid is invalid
                                return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body("Wrong Roomid"));
                            }
                        })
                        .onErrorReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Unknown Room"));
            });
}



    @GetMapping("/{chatid}")
    public Flux<ResponseMessageDTO> getMessages(@PathVariable String chatid, @RequestBody RequestConnection requestConnection )
    {
        if(!requestConnection.getIsGroup())
        {
            return grpcService.getIndivi(chatid).map(v -> toDTO(v));
        }

        return grpcService.getGroup(chatid).map(v -> toDTO(v));

    }

    public ResponseMessageDTO toDTO(ResponseMessage message) {
            ResponseMessageDTO dto = new ResponseMessageDTO();
            dto.setMessageId(null);  // UUID as String
            dto.setChatId(message.getChatName());  // Chat ID
            dto.setSenderId(message.getSenderId());  // Sender ID
            dto.setMessage(message.getMessage());  // Message content
            dto.setSeen(message.getSeen());  // Seen flag
            Timestamp sendTimeTimestamp = message.getSendTime();
            LocalDateTime sendTime = LocalDateTime.ofEpochSecond(
                sendTimeTimestamp.getSeconds(),
                sendTimeTimestamp.getNanos(),
                ZoneOffset.UTC
        );
            dto.setSendTime(sendTime);  // Convert Timestamp to String (or use LocalDateTime)
            return dto;
        }

//    @MessageMapping("/chat.leaveUser")
//    public void leaveUser(@Payload ChatMessage chatMessage) {
//        chatMessage.setType(ChatMessage.MessageType.LEAVE);
//        messagingTemplate.convertAndSend("/topic/" + chatMessage.getRoomId(), chatMessage);
//    }
}
