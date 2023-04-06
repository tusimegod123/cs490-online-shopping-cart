package com.cs490.shoppingcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ShoppingCartServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartServiceRegistryApplication.class, args);
	}

}
