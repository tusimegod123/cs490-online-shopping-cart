package com.cs490.shoppingcart.administrationmodule.config;

import com.cs490.shoppingcart.administrationmodule.model.User;
import com.cs490.shoppingcart.administrationmodule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
        return user;
    }

//    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
//        User user = this.repository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("Email " + userEmail + " not found"));
//        return user;
//    }
}
