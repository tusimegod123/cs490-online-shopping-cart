package com.cs490.shoppingcart.administrationmodule.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}

