package org.example.InidividualMessageService.Services;

import org.example.InidividualMessageService.Models.Messages;
import org.example.InidividualMessageService.Repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatService {

    @Autowired
    private MessageRepo messageRepo;

    public Flux<Messages> getmessages(String Chatid)
    {
        return messageRepo.findByChatID(Chatid);
    }
}
