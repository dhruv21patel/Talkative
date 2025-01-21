package org.example.GroupMessageService.Repository;


import org.example.GroupMessageService.Models.Chats;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ChatRepo extends ReactiveCrudRepository<Chats, UUID> {

    public Mono<Chats> findBychatId(String chatId);
}
