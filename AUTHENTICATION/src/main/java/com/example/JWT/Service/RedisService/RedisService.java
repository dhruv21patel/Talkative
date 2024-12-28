package com.example.JWT.Service.RedisService;

public interface RedisService {

    public <T> T get(String username, Class<T> entityclass);
    public void set(String username, Object o);

}
