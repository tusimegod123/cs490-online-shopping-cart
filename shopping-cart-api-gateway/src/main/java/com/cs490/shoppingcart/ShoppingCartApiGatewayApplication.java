package com.cs490.shoppingcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@SpringBootApplication
@EnableDiscoveryClient
//@EnableHystrix
public class ShoppingCartApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartApiGatewayApplication.class, args);
	}

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		http.csrf(csrf -> csrf.disable());
		return http.build();
	}

}
