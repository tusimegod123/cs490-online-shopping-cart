package com.cs490.shoppingCart.ProductManagementModule.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProductConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Value("${userServiceForLocalHost}")
    @Value("${userServiceUrl}")
    private String userServiceUrl;

    @Bean
    public String userServiceUrl() {
        return userServiceUrl;
    }
}
