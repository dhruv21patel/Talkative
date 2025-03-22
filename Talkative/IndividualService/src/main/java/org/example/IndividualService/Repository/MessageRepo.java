package org.example.IndividualService.Repository;


import org.example.IndividualService.Models.Messages;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@EnableR2dbcRepositories
public interface MessageRepo extends ReactiveCrudRepository<Messages, UUID> {

     Flux<Messages> findByChatID(String Chatid);
}
