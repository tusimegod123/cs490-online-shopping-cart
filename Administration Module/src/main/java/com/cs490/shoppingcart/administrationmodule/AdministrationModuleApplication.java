package com.cs490.shoppingcart.administrationmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.modelmapper.ModelMapper;

//rCqX2JCo
//        godwin@example.comand
@SpringBootApplication
@EnableDiscoveryClient
//@EnableSwagger2
public class AdministrationModuleApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(AdministrationModuleApplication.class, args);
    }

}
