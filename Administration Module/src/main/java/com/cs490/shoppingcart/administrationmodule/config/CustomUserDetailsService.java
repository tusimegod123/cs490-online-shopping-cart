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

//    @Override
//    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//        Optional<User> credential = repository.findByName(name);
//        return credential.map(User::new).orElseThrow(() -> new UsernameNotFoundException("user not found with name :" + name));
//    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = (User)this.repository.findByName(username).orElseThrow(() -> {
            return new UsernameNotFoundException("Username " + username + " not found");
        });
        return user;
    }
}
