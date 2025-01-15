package org.example.ConnectionService.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.example.ConnectionService.Constants.AllConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {


    @Bean
    public NewTopic topic()
    {
        return TopicBuilder
                .name(AllConstants.Topic)
                .partitions(5)
                .build();
    }


}
