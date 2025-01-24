package org.example.GroupMessageService.Repository;

import org.example.GroupMessageService.Models.Chats;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@EnableR2dbcRepositories
public interface ChatRepo extends ReactiveCrudRepository<Chats, String> {

    @Query("INSERT INTO chats (chatid, created_at) VALUES (:chatId, :createdAt) RETURNING *")
    Mono<Chats> insertChat(String chatId, LocalDateTime createdAt);

    Mono<Chats> findByChatId(String chatId);
}
