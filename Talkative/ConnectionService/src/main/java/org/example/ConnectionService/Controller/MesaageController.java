//package org.example.ConnectionService.Controller;
//
//import org.example.ConnectionService.DTO.Messages;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Mono;
//
//import java.util.Objects;
//
//@RestController
//@RequestMapping("/Chat")
//public class MesaageController {
//    @Autowired
//    Chat_id_Service chatIdService;
//
//
//    @PostMapping("/{Roomid}")
//    public Mono<String> SendMessage(@PathVariable String Roomid , @RequestBody Messages messages)
//    {
//        String Chatid;
//        if(messages.getIsGroup() && messages.getGroupname() != null)
//        {
//             Chatid = messages.getGroupname();
//        }else {
//             Chatid = chatIdService.generateChatid(messages.getReceiver_id(), messages.getSender_id());
//        }
//        return chatIdService.GetRoomidRedis(Chatid).map(id -> {
//            if(Objects.equals(id, Roomid))
//            {
//                return messages.getMessage();
//            }
//            throw new RuntimeException(" Opps! , Wrong Room");
//        }).onErrorReturn("Trying to Send to UnAuthorized Room that you are not Part of");
//
//    }
//}
