package com.cs490.shoppingCart.NotificationModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class NotificationModuleApplication implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(NotificationModuleApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(NotificationModuleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		LOG.info("###############################################################################################");
		LOG.info("          Notification Module Spring Boot Application command-line initiated");
		LOG.info("###############################################################################################");
	}
}
