package com.cs490.shoppingcart.administrationmodule.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cs490.shoppingcart.administrationmodule.dto.NotificationRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

class UserTest {

    @Test
    void testConstructor() {
        // Arrange and Act
        User actualUser = new User();
        actualUser.setUsername("janedoe");

        // Assert
        assertEquals("janedoe", actualUser.getUsername());
    }

    @Test
    void testConstructor2() {
        // Arrange and Act
        User actualUser = new User("Name", "jane.doe@example.org", "password", "4105551212", "janedoe", new ArrayList<>());
        actualUser.setUsername("janedoe");

        // Assert
        assertEquals("janedoe", actualUser.getUsername());
    }

    @Test
    void testGetAuthorities() {
        // Arrange, Act and Assert
        assertTrue((new User()).getAuthorities().isEmpty());
    }

    /**
     * Method under test: {@link User#getAuthorities()}
     */
    @Test
    void testGetAuthorities2() {
        // Arrange
        User user = new User();
        user.addRole(new Role("Role Name"));

        // Act
        Collection<? extends GrantedAuthority> actualAuthorities = user.getAuthorities();

        // Assert
        assertEquals(1, actualAuthorities.size());
        assertEquals("Role Name", ((List<? extends GrantedAuthority>) actualAuthorities).get(0).getAuthority());
    }

    /**
     * Method under test: {@link User#addRole(Role)}
     */
    @Test
    void testAddRole() {
        // Arrange
        User user = new User();

        // Act
        user.addRole(new Role("Role Name"));

        // Assert
        assertEquals(1, user.getRoles().size());
    }

    /**
     * Method under test: {@link User#getNotificationRequest(String)}
     */
    @Test
    void testGetNotificationRequest() {
        // Arrange
        User user = new User();

        // Act
        NotificationRequest actualNotificationRequest = user.getNotificationRequest("password");

        // Assert
        assertEquals("WelcomeEmail", actualNotificationRequest.getEmailType());
        assertNull(actualNotificationRequest.getUserId());
        assertEquals("password", actualNotificationRequest.getPassword());
        assertNull(actualNotificationRequest.getMessage());
        assertEquals("WelcomeEmail", user.getEmailType());
    }
}

