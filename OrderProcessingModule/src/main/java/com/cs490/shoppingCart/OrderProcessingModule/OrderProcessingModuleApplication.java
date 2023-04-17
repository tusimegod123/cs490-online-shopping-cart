package com.cs490.shoppingCart.OrderProcessingModule;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class OrderProcessingModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderProcessingModuleApplication.class, args);
	}

}
