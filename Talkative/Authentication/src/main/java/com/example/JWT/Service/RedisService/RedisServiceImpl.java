package com.example.JWT.Service.RedisService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService{

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public <T> T get(String username,Class<T> entityclass) {
        try{
            Object value =  redisTemplate.opsForValue().get(username);
            System.out.printf(value.toString());
            if (entityclass.isInstance(value)) {
                System.out.println("CHECKING");
                return entityclass.cast(value);
            }
            throw new IllegalArgumentException("Value stored in Redis is not of type: " + entityclass.getName());
//            Object o = redisTemplate.opsForValue().get(username);
//            ObjectMapper mapper = new ObjectMapper();
//            return mapper.readValue(o.toString(),entityclass);
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public void set(String username, Object o) {
        try{
//            ObjectMapper mapper = new ObjectMapper();
//            String jsonvalue = mapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(username,o,3600, TimeUnit.SECONDS);
        }
        catch (Exception e)
        {
            return;
        }

    }



}
