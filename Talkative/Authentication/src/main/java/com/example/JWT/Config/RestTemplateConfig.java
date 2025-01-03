package com.example.JWT.Config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    // Define a @Bean for RestTemplate
    @Bean
    @LoadBalanced // Enables client-side load balancing
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(5)) // Connection timeout
                .setReadTimeout(Duration.ofSeconds(5))    // Read timeout
                .defaultHeader("Content-Type", "application/json") // Default header
                .build(); // Build and return RestTemplate
    }
}
