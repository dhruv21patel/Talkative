package org.example.GroupMessageService.Repository;

import org.example.GroupMessageService.Models.Members;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@EnableR2dbcRepositories
public interface MemberRepo extends ReactiveCrudRepository<Members, UUID> {
    @Query("INSERT INTO group_members (id, chatid, userid, role, joined_at) VALUES (:id, :chatId, :userId, :role, :joinedAt) RETURNING *")
    Mono<Members> insertMember(UUID id, String chatId, Long userId, String role, LocalDateTime joinedAt);

    Flux<Members> findByChatId(String chatId);
}
