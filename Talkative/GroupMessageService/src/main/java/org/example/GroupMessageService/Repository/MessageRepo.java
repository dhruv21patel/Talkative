package org.example.GroupMessageService.Repository;

import org.example.GroupMessageService.Models.Messages;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@EnableR2dbcRepositories
public interface MessageRepo extends ReactiveCrudRepository<Messages, UUID> {
    Flux<Messages> findByChatId(String chatId);
}
