package com.cs490.shoppingCart.OrderProcessingModule.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemp {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
