package com.cs490.shoppingcart.administrationmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//rCqX2JCo
//        godwin@example.comand
@SpringBootApplication
@EnableDiscoveryClient
//@EnableSwagger2
public class AdministrationModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdministrationModuleApplication.class, args);
    }

}
