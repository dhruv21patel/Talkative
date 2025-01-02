package com.example.JWT.Config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig extends RestTemplate {

    public RestTemplateBuilder restTemplate(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder;
    }
}
