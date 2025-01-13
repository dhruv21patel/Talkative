package org.example.ConnectionService.Config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Configuration
public class MongoClientConfig {

    @Value("${Mongo_Database}")
    private String mongoDatabase;

    @Value("${MongoClientUri}")
    private String mongoConnectionUri;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoConnectionUri);
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate(MongoClient mongoClient) {
        return new ReactiveMongoTemplate(mongoClient, mongoDatabase);
    }
}
