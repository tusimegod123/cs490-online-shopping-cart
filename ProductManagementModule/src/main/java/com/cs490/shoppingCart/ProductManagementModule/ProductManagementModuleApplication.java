package com.cs490.shoppingCart.ProductManagementModule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductManagementModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductManagementModuleApplication.class, args);
	}

}
