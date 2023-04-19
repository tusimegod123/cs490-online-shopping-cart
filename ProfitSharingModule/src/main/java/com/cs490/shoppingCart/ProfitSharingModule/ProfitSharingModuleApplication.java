package com.cs490.shoppingCart.ProfitSharingModule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ProfitSharingModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfitSharingModuleApplication.class, args);
	}

	 @LoadBalanced
     @Bean
     public RestTemplate restTemplate() {
      return new RestTemplate();
     }
}
