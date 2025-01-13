package org.example.ConnectionService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class ConnectionService
{
    public static void main( String[] args )
    {
        SpringApplication.run(ConnectionService.class,args);
    }
}
