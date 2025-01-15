package org.example.ConnectionService.Service;

import org.example.ConnectionService.DTO.ChatRoom;
import org.example.ConnectionService.DTO.RequestConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class RoomMapper {

  @Autowired
ReactiveRedisTemplate<String, ChatRoom> redisRoomTemplate;

public Mono<Boolean> Save(String chatid, ChatRoom room ) {
    return redisRoomTemplate.opsForValue().set(chatid, room)
            .retry(3) // Retry Redis operation up to 3 times
            .flatMap(success -> {
                if (success) {
                    return Mono.just(true); // Successfully stored in Redis
                } else {
                    return Mono.error(new RuntimeException("Failed to create TempRoom: Redis set operation returned false"));
                }
            })
            .onErrorResume(e -> {
                // Handle all errors by returning false
                System.err.println("Error saving to Redis: " + e.getMessage());
                return Mono.just(false);
            });
}
    public Mono<ChatRoom> getRoomid(String Chatid)
    {
        return redisRoomTemplate.opsForValue().get(Chatid).switchIfEmpty(
                Mono.just(ChatRoom.builder().Chatid(Chatid).Roomid(GenerateRoomId()).build()));
    }

    public Mono<String> GenerateChatId(RequestConnection requestConnection)
    {
        if(requestConnection.getIsGroup() && requestConnection.getGroupname() != null)
        {
            return Mono.just(requestConnection.getGroupname());
        }

        Long sender = requestConnection.getSender_id();
        Long receiver = requestConnection.getReceiver_id();
        return  Mono.just(Math.min(sender,receiver)+"-"+Math.max(sender,receiver));
    }

    private String GenerateRoomId()
    {
        String roomid = UUID.randomUUID().toString();
        return  roomid;
    }

    public Mono<Boolean> deleteRoom(String chatid)
    {
        return redisRoomTemplate.opsForValue().delete(chatid);
    }
}
