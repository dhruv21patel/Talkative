package org.example.GroupMessageService.Controller;

import org.example.GroupMessageService.DTO.AddMembersDTO;
import org.example.GroupMessageService.DTO.CreateGroupDTO;
import org.example.GroupMessageService.DTO.SourceMessage;
import org.example.GroupMessageService.Services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ReceiverService {


    @Autowired
    ChatService chatService;

    @GetMapping(value = "/",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getMessages()
    {
        return null;
    }

    @PostMapping(value = "/CreateGroup")
    public Mono<String> createGroup(@RequestBody CreateGroupDTO createGroupDTO)
    {
        return chatService.createChat(createGroupDTO.getGroupName())
                            .flatMap(chat -> chatService.addMember(chat.getChatId(), createGroupDTO.getCreatorId(), "Admin"))
                            .doOnSuccess(s -> System.out.println("Group created for ChatId: " + createGroupDTO.getGroupName() +
                                                                 " and Creator ID: " + createGroupDTO.getCreatorId()))
                            .then(Mono.just("Group Created"));

    }

    @PostMapping(value = "/AddMembers")
    public Mono<String> addMembers(@RequestBody AddMembersDTO addMembersDTO)
    {
        return Flux.fromIterable(addMembersDTO.getMembersList()) // Convert List to Flux
            .flatMap(member -> chatService.addMember(addMembersDTO.getChatid(), member, "User")) // Call addMember for each user
            .then(Mono.just("Members Added")); // ✅ Convert to Mono<String> after all operations complete
    }
}
