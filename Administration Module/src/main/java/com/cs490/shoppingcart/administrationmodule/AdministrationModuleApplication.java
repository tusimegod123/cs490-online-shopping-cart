package com.cs490.shoppingcart.administrationmodule;

import com.cs490.shoppingcart.administrationmodule.model.Role;
import com.cs490.shoppingcart.administrationmodule.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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
public class AdministrationModuleApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(AdministrationModuleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Role admin = new Role(1,"ADMIN");
        roleRepository.save(admin);
        Role vendor = new Role(2,"VENDOR");
        roleRepository.save(vendor);
        Role customer = new Role(3, "REGISTERED_USER");
        roleRepository.save(customer);
        Role guest = new Role( 4,"GUEST");
        roleRepository.save(guest);
    }
}
