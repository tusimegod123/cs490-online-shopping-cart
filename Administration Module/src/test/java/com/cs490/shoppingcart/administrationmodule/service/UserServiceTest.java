package com.cs490.shoppingcart.administrationmodule.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cs490.shoppingcart.administrationmodule.exception.InvalidCredentialsException;
import com.cs490.shoppingcart.administrationmodule.exception.NotVerifiedException;
import com.cs490.shoppingcart.administrationmodule.exception.UserNotFoundException;
import com.cs490.shoppingcart.administrationmodule.model.User;
import com.cs490.shoppingcart.administrationmodule.repository.RoleRepository;
import com.cs490.shoppingcart.administrationmodule.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @MockBean
    private JwtService jwtService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RoleService roleService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Method under test: {@link UserService#UserService(UserRepository, PasswordEncoder, RoleService, JwtService)}
     */
    @Test
    void testConstructor() {
        UserRepository userRepository = mock(UserRepository.class);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        RoleService roleService = new RoleService(mock(RoleRepository.class));
        assertEquals("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437", (new UserService(userRepository,
                passwordEncoder, roleService, new JwtService(mock(UserRepository.class)))).SECRET);
    }


    /**
     * Method under test: {@link UserService#generateRandomUsername(int)}
     */
    @Test
    void testGenerateRandomUsername2() {

        assertEquals("", userService.generateRandomUsername(0));
    }

    /**
     * Method under test: {@link UserService#createUser(User)}
     */
    @Test
    void testCreateUser() {
        when(userRepository.existsByEmail((String) any())).thenReturn(true);
        when(userRepository.save((User) any())).thenReturn(new User());
        ResponseEntity<?> actualCreateUserResult = userService.createUser(new User());
        assertEquals("A user with this email already exists", actualCreateUserResult.getBody());
        assertEquals(400, actualCreateUserResult.getStatusCodeValue());
        assertTrue(actualCreateUserResult.getHeaders().isEmpty());
        verify(userRepository).existsByEmail((String) any());
    }

    /**
     * Method under test: {@link UserService#verifyVendor(User, Long, User)}
     */
    @Test
    void testVerifyVendor() throws UserNotFoundException {

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        RoleService roleService = new RoleService(mock(RoleRepository.class));
        UserService userService = new UserService(userRepository, passwordEncoder, roleService,
                new JwtService(mock(UserRepository.class)));
        assertThrows(UserNotFoundException.class, () -> userService.verifyVendor(new User(), 1L, null));
        verify(userRepository).findById((Long) any());
    }


    /**
     * Method under test: {@link UserService#fullyVerifyVendor(Long)}
     */
    @Test
    void testFullyVerifyVendor2() throws NotVerifiedException {
        User user = new User();
        user.setIsVerified(true);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(jwtService.refreshToken((String) any())).thenReturn("ABC123");
        ResponseEntity<String> actualFullyVerifyVendorResult = userService.fullyVerifyVendor(123L);
        assertEquals("ABC123", actualFullyVerifyVendorResult.getBody());
        assertEquals(200, actualFullyVerifyVendorResult.getStatusCodeValue());
        assertTrue(actualFullyVerifyVendorResult.getHeaders().isEmpty());
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
        verify(jwtService).refreshToken((String) any());
    }

    /**
     * Method under test: {@link UserService#updateUserById(Long, User)}
     */
    @Test
    void testUpdateUserById() throws UserNotFoundException {
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.updateUserById(123L, new User()));
        verify(userRepository).findById((Long) any());
    }


    /**
     * Method under test: {@link UserService#findUser(Long)}
     */
    @Test
    void testFindUser() {
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());
        ResponseEntity<?> actualFindUserResult = userService.findUser(123L);
        assertEquals("{\"Sorry\":\"Sorry this does not exist in our system\"}", actualFindUserResult.getBody());
        assertEquals(404, actualFindUserResult.getStatusCodeValue());
        assertTrue(actualFindUserResult.getHeaders().isEmpty());
        verify(userRepository).findById((Long) any());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).delete((User) any());
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        ResponseEntity<?> actualDeleteUserResult = userService.deleteUser(123L);
        assertEquals("User Successfully deleted", actualDeleteUserResult.getBody());
        assertEquals(200, actualDeleteUserResult.getStatusCodeValue());
        assertTrue(actualDeleteUserResult.getHeaders().isEmpty());
        verify(userRepository).findById((Long) any());
        verify(userRepository).delete((User) any());
    }

    /**
     * Method under test: {@link UserService#allUsers()}
     */
    @Test
    void testAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);
        List<User> actualAllUsersResult = userService.allUsers();
        assertSame(userList, actualAllUsersResult);
        assertTrue(actualAllUsersResult.isEmpty());
        verify(userRepository).findAll();
    }


    /**
     * Method under test: {@link UserService#generateToken(String)}
     */
    @Test
    void testGenerateToken() throws InvalidCredentialsException {
        when(jwtService.generateToken((String) any())).thenReturn("ABC123");
        assertEquals("ABC123", userService.generateToken("janedoe"));
        verify(jwtService).generateToken((String) any());
    }


    /**
     * Method under test: {@link UserService#validateToken(String)}
     */
    @Test
    void testValidateToken() {
        when(jwtService.validateToken((String) any())).thenReturn(true);
        userService.validateToken("ABC123");
        verify(jwtService).validateToken((String) any());
    }

}

