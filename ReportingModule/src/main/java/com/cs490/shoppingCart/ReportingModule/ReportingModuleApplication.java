package com.cs490.shoppingCart.ReportingModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ReportingModuleApplication implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(ReportingModuleApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ReportingModuleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		LOG.info("###############################################################################################");
		LOG.info("          Reporting Module Spring Boot Application command-line initiated");
		LOG.info("###############################################################################################");
	}

	@RestController
	class RESTController {

		@GetMapping("/")
		public String hello() {
			return "<H1>Reporting Module Spring Boot Application Activated</H1>";
		}
	}
}
