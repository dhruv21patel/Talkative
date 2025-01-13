package org.example.ConnectionService.Exception;

public class RedisException extends RuntimeException{
     public RedisException(String Error)
     {
         super(Error);
     }

}
