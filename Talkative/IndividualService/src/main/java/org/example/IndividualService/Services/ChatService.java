package org.example.IndividualService.Services;

import org.example.IndividualService.Models.Messages;
import org.example.IndividualService.Repository.MessageRepo;
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
