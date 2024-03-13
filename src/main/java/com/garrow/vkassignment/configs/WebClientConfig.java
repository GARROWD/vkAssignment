package com.garrow.vkassignment.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    private final String URL = "https://jsonplaceholder.typicode.com";

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl("https://jsonplaceholder.typicode.com").build();
    }
}
