package org.example.ConnectionService.Repository;

import org.example.ConnectionService.Models.SessionTable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@EnableReactiveMongoRepositories
public interface SessionTableMongoRepo extends ReactiveMongoRepository<SessionTable,Long> {
    public Mono<SessionTable> findByUserID(Long userId);
    public Flux<SessionTable> findByRoomID(String roomID);
    public Mono<Boolean> deleteByUserID(Long userID);
    public Mono<Boolean> deleteByRoomID(String roomID);
}
