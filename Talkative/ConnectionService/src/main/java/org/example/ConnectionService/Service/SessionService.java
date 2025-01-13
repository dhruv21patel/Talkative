package org.example.ConnectionService.Service;

import org.example.ConnectionService.Models.SessionTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SessionService {

    @Autowired
    ReactiveRedisTemplate<String,SessionTable> redisSessionTemplate;

    public Mono<Boolean> SaveSession(SessionTable S,String userId)
    {
        return redisSessionTemplate.opsForValue().set(userId,S).retry(3).onErrorReturn(false);
    }

    public Mono<SessionTable> GetSession(String Userid)
    {
        System.out.println("Getting Session for Userid"+" "+ Userid+" of Type " + Userid.getClass());
        return redisSessionTemplate.opsForValue().get(Userid).switchIfEmpty(Mono.empty());
    }

    public Mono<Boolean> DeleteSession(String Userid)
    {
        return redisSessionTemplate.opsForValue().delete(Userid).retry(3).onErrorReturn(false);
    }
}
