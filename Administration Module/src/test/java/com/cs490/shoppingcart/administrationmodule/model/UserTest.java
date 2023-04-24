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
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link User#User()}
     *   <li>{@link User#setUsername(String)}
     *   <li>{@link User#getUsername()}
     * </ul>
     */
    @Test
    void testConstructor() {
        // Arrange and Act
        User actualUser = new User();
        actualUser.setUsername("janedoe");

        // Assert
        assertEquals("janedoe", actualUser.getUsername());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link User#User(String, String, String, String, String, List)}
     *   <li>{@link User#setUsername(String)}
     *   <li>{@link User#getUsername()}
     * </ul>
     */
    @Test
    void testConstructor2() {
        // Arrange and Act
        User actualUser = new User("Name", "jane.doe@example.org", "iloveyou", "4105551212", "janedoe", new ArrayList<>());
        actualUser.setUsername("janedoe");

        // Assert
        assertEquals("janedoe", actualUser.getUsername());
    }

    /**
     * Method under test: {@link User#getAuthorities()}
     */
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
     * Method under test: {@link User#getAuthorities()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAuthorities3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: A granted authority textual representation is required
        //       at org.springframework.util.Assert.hasText(Assert.java:294)
        //       at org.springframework.security.core.authority.SimpleGrantedAuthority.<init>(SimpleGrantedAuthority.java:39)
        //       at com.cs490.shoppingcart.administrationmodule.model.User.getAuthorities(User.java:81)
        //   In order to prevent getAuthorities()
        //   from throwing IllegalArgumentException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   getAuthorities().
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        User user = new User();
        user.addRole(new Role(""));

        // Act
        user.getAuthorities();
    }

    /**
     * Method under test: {@link User#getAuthorities()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAuthorities4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.cs490.shoppingcart.administrationmodule.model.Role.getRoleName()" because "role" is null
        //       at com.cs490.shoppingcart.administrationmodule.model.User.getAuthorities(User.java:81)
        //   In order to prevent getAuthorities()
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   getAuthorities().
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        User user = new User();
        user.addRole(null);

        // Act
        user.getAuthorities();
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
        NotificationRequest actualNotificationRequest = user.getNotificationRequest("iloveyou");

        // Assert
        assertEquals("WelcomeEmail", actualNotificationRequest.getEmailType());
        assertNull(actualNotificationRequest.getUserId());
        assertEquals("iloveyou", actualNotificationRequest.getPassword());
        assertNull(actualNotificationRequest.getMessage());
        assertEquals("WelcomeEmail", user.getEmailType());
    }
}

