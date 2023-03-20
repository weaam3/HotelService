package com.example.hotelservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${services.database.controller}")
    private String databaseController;
    @Value("${services.auth.controller}")
    private String authController;

    @Bean
    public WebClient databaseWebClient(WebClient.Builder webClient) {
        return webClient.baseUrl(databaseController).build();
    }
    @Bean
    WebClient authWebClient(WebClient.Builder webclient){
        return webclient.baseUrl(authController).build();
    }
}
