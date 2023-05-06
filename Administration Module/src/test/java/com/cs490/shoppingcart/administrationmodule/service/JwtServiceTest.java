package com.cs490.shoppingcart.administrationmodule.service;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.cs490.shoppingcart.administrationmodule.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JwtService.class})
@ExtendWith(SpringExtension.class)
class JwtServiceTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;


    @Test
    void testValidateToken() {
        // Arrange, Act and Assert
        assertFalse(jwtService.validateToken("ABC123"));
        assertFalse(jwtService.validateToken(""));
    }
}

