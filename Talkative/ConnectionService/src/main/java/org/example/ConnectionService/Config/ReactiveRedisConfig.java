package org.example.ConnectionService.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.ConnectionService.DTO.ChatRoom;
import org.example.ConnectionService.DTO.Messages;
import org.example.ConnectionService.Models.SessionTable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class ReactiveRedisConfig {


    @Bean
    public ReactiveRedisTemplate<String, ChatRoom> redisRoomTemplate(ReactiveRedisConnectionFactory factory) {

        StringRedisSerializer serializer = new StringRedisSerializer();

        Jackson2JsonRedisSerializer<ChatRoom> ValueSerializer = new Jackson2JsonRedisSerializer<>(ChatRoom.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, ChatRoom> builder =
                RedisSerializationContext.newSerializationContext(serializer);

        RedisSerializationContext<String, ChatRoom> context = builder
                .key(serializer) // Key serializer
                .value(ValueSerializer) // Value serializer
                .hashKey(serializer) // Hash key serializer
                .hashValue(ValueSerializer) // Hash value serializer
                .build();

        return new ReactiveRedisTemplate<String,ChatRoom>(factory, context);
    }

    @Bean
    public ReactiveRedisTemplate<String, SessionTable> redisSessionTemplate(ReactiveRedisConnectionFactory factory) {

        StringRedisSerializer keySerializer = new StringRedisSerializer();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Jackson2JsonRedisSerializer<SessionTable> valueSerializer = new Jackson2JsonRedisSerializer<>(SessionTable.class);
        valueSerializer.setObjectMapper(objectMapper);

        RedisSerializationContext.RedisSerializationContextBuilder<String, SessionTable> builder =
                RedisSerializationContext.newSerializationContext(keySerializer);

        RedisSerializationContext<String, SessionTable> context = builder
                .key(keySerializer) // Key serializer
                .value(valueSerializer) // Value serializer for JSON
                .hashKey(keySerializer) // Hash key serializer
                .hashValue(valueSerializer) // Hash value serializer
                .build();

        return new ReactiveRedisTemplate<String, SessionTable>(factory, context);
    }

    @Bean
    public ReactiveRedisTemplate<String, Messages> redisMessageTemplate(ReactiveRedisConnectionFactory factory)
    {
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Messages> serializer = new Jackson2JsonRedisSerializer<>(Messages.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Messages> builder =
                RedisSerializationContext.newSerializationContext(keySerializer);

        RedisSerializationContext<String,Messages> context =  builder.key(keySerializer)
                .value(serializer)
                .hashKey(keySerializer)
                .hashValue(serializer)
                .build();

        return new ReactiveRedisTemplate<String,Messages>(factory, context);
    }

}
