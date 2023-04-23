package com.cs490.shoppingcart.administrationmodule.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cs490.shoppingcart.administrationmodule.dto.NotificationRequest;
import com.cs490.shoppingcart.administrationmodule.exception.InvalidCredentialsException;
import com.cs490.shoppingcart.administrationmodule.exception.NotVerifiedException;
import com.cs490.shoppingcart.administrationmodule.exception.UserNotFoundException;
import com.cs490.shoppingcart.administrationmodule.model.Role;
import com.cs490.shoppingcart.administrationmodule.model.User;
import com.cs490.shoppingcart.administrationmodule.repository.RoleRepository;
import com.cs490.shoppingcart.administrationmodule.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

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
     * Method under test: {@link UserService#generateRandomPassword(int)}
     */
    @Test
    void testGenerateRandomPassword() {
        // TODO: Complete this test.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by generateRandomPassword(int)
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        userService.generateRandomPassword(3);
    }

    /**
     * Method under test: {@link UserService#generateRandomPassword(int)}
     */
    @Test
    void testGenerateRandomPassword2() {
        assertEquals("", userService.generateRandomPassword(0));
    }

    /**
     * Method under test: {@link UserService#generateRandomPassword(int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGenerateRandomPassword3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: Requested random string length -1 is less than 0.
        //       at org.apache.commons.lang3.RandomStringUtils.random(RandomStringUtils.java:366)
        //       at org.apache.commons.lang3.RandomStringUtils.random(RandomStringUtils.java:474)
        //       at org.apache.commons.lang3.RandomStringUtils.random(RandomStringUtils.java:455)
        //       at com.cs490.shoppingcart.administrationmodule.service.UserService.generateRandomPassword(UserService.java:55)
        //   In order to prevent generateRandomPassword(int)
        //   from throwing IllegalArgumentException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   generateRandomPassword(int).
        //   See https://diff.blue/R013 to resolve this issue.

        userService.generateRandomPassword(-1);
    }

    /**
     * Method under test: {@link UserService#generateRandomUsername(int)}
     */
    @Test
    void testGenerateRandomUsername() {
        // TODO: Complete this test.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by generateRandomUsername(int)
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        userService.generateRandomUsername(3);
    }

    /**
     * Method under test: {@link UserService#generateRandomUsername(int)}
     */
    @Test
    void testGenerateRandomUsername2() {
        assertEquals("", userService.generateRandomUsername(0));
    }

    /**
     * Method under test: {@link UserService#generateRandomUsername(int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGenerateRandomUsername3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: Requested random string length -1 is less than 0.
        //       at org.apache.commons.lang3.RandomStringUtils.random(RandomStringUtils.java:366)
        //       at org.apache.commons.lang3.RandomStringUtils.random(RandomStringUtils.java:474)
        //       at org.apache.commons.lang3.RandomStringUtils.random(RandomStringUtils.java:455)
        //       at com.cs490.shoppingcart.administrationmodule.service.UserService.generateRandomUsername(UserService.java:60)
        //   In order to prevent generateRandomUsername(int)
        //   from throwing IllegalArgumentException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   generateRandomUsername(int).
        //   See https://diff.blue/R013 to resolve this issue.

        userService.generateRandomUsername(-1);
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
     * Method under test: {@link UserService#createUser(User)}
     */
    @Test
    void testCreateUser2() {
        when(userRepository.existsByEmail((String) any())).thenReturn(false);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        ResponseEntity<?> actualCreateUserResult = userService.createUser(new User());
        assertTrue(actualCreateUserResult.hasBody());
        assertEquals(200, actualCreateUserResult.getStatusCodeValue());
        assertTrue(actualCreateUserResult.getHeaders().isEmpty());
        assertTrue(((User) actualCreateUserResult.getBody()).getIsVerified());
        assertTrue(((User) actualCreateUserResult.getBody()).isAccountNonExpired());
        assertEquals("Self-registration, Admin", ((User) actualCreateUserResult.getBody()).getVerifiedBy());
        assertNull(((User) actualCreateUserResult.getBody()).getUsername());
        assertNull(((User) actualCreateUserResult.getBody()).getTelephoneNumber());
        assertTrue(((User) actualCreateUserResult.getBody()).getRoles().isEmpty());
        assertEquals("secret", ((User) actualCreateUserResult.getBody()).getPassword());
        assertNull(((User) actualCreateUserResult.getBody()).getName());
        assertTrue(((User) actualCreateUserResult.getBody()).getIsFullyVerified());
        assertNull(((User) actualCreateUserResult.getBody()).getEmail());
        assertTrue(((User) actualCreateUserResult.getBody()).isEnabled());
        assertTrue(((User) actualCreateUserResult.getBody()).isCredentialsNonExpired());
        assertTrue(((User) actualCreateUserResult.getBody()).isAccountNonLocked());
        verify(userRepository).existsByEmail((String) any());
        verify(userRepository).save((User) any());
        verify(passwordEncoder).encode((CharSequence) any());
    }

    /**
     * Method under test: {@link UserService#createUser(User)}
     */
    @Test
    void testCreateUser3() {
        when(userRepository.existsByEmail((String) any())).thenReturn(false);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        ResponseEntity<?> actualCreateUserResult = userService.createUser(null);
        assertEquals("An error occurred while creating the user", actualCreateUserResult.getBody());
        assertEquals(500, actualCreateUserResult.getStatusCodeValue());
        assertTrue(actualCreateUserResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link UserService#createUser(User)}
     */
    @Test
    void testCreateUser4() {
        when(userRepository.existsByEmail((String) any())).thenReturn(false);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(roleService.findRole((Integer) any())).thenReturn(new Role("Role Name"));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");

        User user = new User();
        user.addRole(new Role("Self-registration, Admin"));
        ResponseEntity<?> actualCreateUserResult = userService.createUser(user);
        assertTrue(actualCreateUserResult.hasBody());
        assertEquals(200, actualCreateUserResult.getStatusCodeValue());
        assertTrue(actualCreateUserResult.getHeaders().isEmpty());
        assertTrue(((User) actualCreateUserResult.getBody()).getIsVerified());
        assertTrue(((User) actualCreateUserResult.getBody()).isAccountNonExpired());
        assertEquals("Self-registration, Admin", ((User) actualCreateUserResult.getBody()).getVerifiedBy());
        assertNull(((User) actualCreateUserResult.getBody()).getUsername());
        assertNull(((User) actualCreateUserResult.getBody()).getTelephoneNumber());
        List<Role> roles = ((User) actualCreateUserResult.getBody()).getRoles();
        assertEquals(1, roles.size());
        assertEquals("secret", ((User) actualCreateUserResult.getBody()).getPassword());
        assertTrue(((User) actualCreateUserResult.getBody()).isAccountNonLocked());
        assertNull(((User) actualCreateUserResult.getBody()).getName());
        assertTrue(((User) actualCreateUserResult.getBody()).isEnabled());
        assertNull(((User) actualCreateUserResult.getBody()).getEmail());
        assertTrue(((User) actualCreateUserResult.getBody()).getIsFullyVerified());
        assertTrue(((User) actualCreateUserResult.getBody()).isCredentialsNonExpired());
        assertNull(roles.get(0).getRoleId());
        verify(userRepository).existsByEmail((String) any());
        verify(userRepository).save((User) any());
        verify(roleService).findRole((Integer) any());
        verify(passwordEncoder).encode((CharSequence) any());
    }

    /**
     * Method under test: {@link UserService#createUser(User)}
     */
    @Test
    void testCreateUser5() {
        when(userRepository.existsByEmail((String) any())).thenReturn(false);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(roleService.findRole((Integer) any())).thenReturn(new Role("VENDOR"));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");

        User user = new User();
        user.addRole(new Role("Self-registration, Admin"));
        ResponseEntity<?> actualCreateUserResult = userService.createUser(user);
        assertTrue(actualCreateUserResult.hasBody());
        assertEquals(200, actualCreateUserResult.getStatusCodeValue());
        assertTrue(actualCreateUserResult.getHeaders().isEmpty());
        assertFalse(((User) actualCreateUserResult.getBody()).getIsVerified());
        assertTrue(((User) actualCreateUserResult.getBody()).isAccountNonExpired());
        assertNull(((User) actualCreateUserResult.getBody()).getUsername());
        assertNull(((User) actualCreateUserResult.getBody()).getTelephoneNumber());
        List<Role> roles = ((User) actualCreateUserResult.getBody()).getRoles();
        assertEquals(1, roles.size());
        assertTrue(((User) actualCreateUserResult.getBody()).isAccountNonLocked());
        assertNull(((User) actualCreateUserResult.getBody()).getName());
        assertTrue(((User) actualCreateUserResult.getBody()).isEnabled());
        assertNull(((User) actualCreateUserResult.getBody()).getEmail());
        assertFalse(((User) actualCreateUserResult.getBody()).getIsFullyVerified());
        assertTrue(((User) actualCreateUserResult.getBody()).isCredentialsNonExpired());
        assertNull(roles.get(0).getRoleId());
        verify(userRepository).existsByEmail((String) any());
        verify(userRepository).save((User) any());
        verify(roleService).findRole((Integer) any());
    }

    /**
     * Method under test: {@link UserService#createUser(User)}
     */
    @Test
    void testCreateUser6() {
        when(userRepository.existsByEmail((String) any())).thenReturn(false);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(roleService.findRole((Integer) any())).thenReturn(new Role("GUEST"));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");

        User user = new User();
        user.addRole(new Role("Self-registration, Admin"));
        ResponseEntity<?> actualCreateUserResult = userService.createUser(user);
        assertTrue(actualCreateUserResult.hasBody());
        assertEquals(200, actualCreateUserResult.getStatusCodeValue());
        assertTrue(actualCreateUserResult.getHeaders().isEmpty());
        assertFalse(((User) actualCreateUserResult.getBody()).getIsVerified());
        assertTrue(((User) actualCreateUserResult.getBody()).isAccountNonExpired());
        assertNull(((User) actualCreateUserResult.getBody()).getUsername());
        assertNull(((User) actualCreateUserResult.getBody()).getTelephoneNumber());
        List<Role> roles = ((User) actualCreateUserResult.getBody()).getRoles();
        assertEquals(1, roles.size());
        assertTrue(((User) actualCreateUserResult.getBody()).isAccountNonLocked());
        assertNull(((User) actualCreateUserResult.getBody()).getName());
        assertTrue(((User) actualCreateUserResult.getBody()).isEnabled());
        assertNull(((User) actualCreateUserResult.getBody()).getEmail());
        assertFalse(((User) actualCreateUserResult.getBody()).getIsFullyVerified());
        assertTrue(((User) actualCreateUserResult.getBody()).isCredentialsNonExpired());
        assertNull(roles.get(0).getRoleId());
        verify(userRepository).existsByEmail((String) any());
        verify(userRepository).save((User) any());
        verify(roleService).findRole((Integer) any());
    }

    /**
     * Method under test: {@link UserService#createUser(User)}
     */
    @Test
    void testCreateUser7() {
        when(userRepository.existsByEmail((String) any())).thenReturn(false);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(roleService.findRole((Integer) any())).thenReturn(new Role("REGISTERED_USER"));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");

        User user = new User();
        user.addRole(new Role("Self-registration, Admin"));
        ResponseEntity<?> actualCreateUserResult = userService.createUser(user);
        assertTrue(actualCreateUserResult.hasBody());
        assertEquals(200, actualCreateUserResult.getStatusCodeValue());
        assertTrue(actualCreateUserResult.getHeaders().isEmpty());
        assertTrue(((User) actualCreateUserResult.getBody()).getIsVerified());
        assertTrue(((User) actualCreateUserResult.getBody()).isAccountNonExpired());
        assertEquals("Self-registration, Customer", ((User) actualCreateUserResult.getBody()).getVerifiedBy());
        assertNull(((User) actualCreateUserResult.getBody()).getUsername());
        assertNull(((User) actualCreateUserResult.getBody()).getTelephoneNumber());
        List<Role> roles = ((User) actualCreateUserResult.getBody()).getRoles();
        assertEquals(1, roles.size());
        assertEquals("secret", ((User) actualCreateUserResult.getBody()).getPassword());
        assertTrue(((User) actualCreateUserResult.getBody()).isAccountNonLocked());
        assertNull(((User) actualCreateUserResult.getBody()).getName());
        assertTrue(((User) actualCreateUserResult.getBody()).isEnabled());
        assertNull(((User) actualCreateUserResult.getBody()).getEmail());
        assertTrue(((User) actualCreateUserResult.getBody()).getIsFullyVerified());
        assertTrue(((User) actualCreateUserResult.getBody()).isCredentialsNonExpired());
        assertNull(roles.get(0).getRoleId());
        verify(userRepository).existsByEmail((String) any());
        verify(userRepository).save((User) any());
        verify(roleService).findRole((Integer) any());
        verify(passwordEncoder).encode((CharSequence) any());
    }

    /**
     * Method under test: {@link UserService#createUser(User)}
     */
    @Test
    void testCreateUser8() {
        when(userRepository.existsByEmail((String) any())).thenReturn(false);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(roleService.findRole((Integer) any())).thenReturn(null);
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");

        User user = new User();
        user.addRole(new Role("Self-registration, Admin"));
        ResponseEntity<?> actualCreateUserResult = userService.createUser(user);
        assertEquals("An error occurred while creating the user", actualCreateUserResult.getBody());
        assertEquals(500, actualCreateUserResult.getStatusCodeValue());
        assertTrue(actualCreateUserResult.getHeaders().isEmpty());
        verify(userRepository).existsByEmail((String) any());
        verify(roleService).findRole((Integer) any());
    }

    /**
     * Method under test: {@link UserService#createUser(User)}
     */
    @Test
    void testCreateUser9() {
        when(userRepository.existsByEmail((String) any())).thenReturn(false);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(roleService.findRole((Integer) any())).thenReturn(new Role("Role Name"));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");

        User user = new User();
        user.addRole(new Role("VENDOR"));
        user.addRole(new Role("Self-registration, Admin"));
        ResponseEntity<?> actualCreateUserResult = userService.createUser(user);
        assertTrue(actualCreateUserResult.hasBody());
        assertEquals(200, actualCreateUserResult.getStatusCodeValue());
        assertTrue(actualCreateUserResult.getHeaders().isEmpty());
        assertTrue(((User) actualCreateUserResult.getBody()).getIsVerified());
        assertTrue(((User) actualCreateUserResult.getBody()).isAccountNonExpired());
        assertEquals("Self-registration, Admin", ((User) actualCreateUserResult.getBody()).getVerifiedBy());
        assertNull(((User) actualCreateUserResult.getBody()).getUsername());
        assertNull(((User) actualCreateUserResult.getBody()).getTelephoneNumber());
        List<Role> roles = ((User) actualCreateUserResult.getBody()).getRoles();
        assertEquals(2, roles.size());
        assertEquals("secret", ((User) actualCreateUserResult.getBody()).getPassword());
        assertTrue(((User) actualCreateUserResult.getBody()).isAccountNonLocked());
        assertTrue(((User) actualCreateUserResult.getBody()).isEnabled());
        assertNull(((User) actualCreateUserResult.getBody()).getName());
        assertTrue(((User) actualCreateUserResult.getBody()).getIsFullyVerified());
        assertNull(((User) actualCreateUserResult.getBody()).getEmail());
        assertTrue(((User) actualCreateUserResult.getBody()).isCredentialsNonExpired());
        assertNull(roles.get(1).getRoleId());
        verify(userRepository).existsByEmail((String) any());
        verify(userRepository).save((User) any());
        verify(roleService, atLeast(1)).findRole((Integer) any());
        verify(passwordEncoder).encode((CharSequence) any());
    }

    /**
     * Method under test: {@link UserService#createUser(User)}
     */
    @Test
    void testCreateUser10() {
        when(userRepository.existsByEmail((String) any())).thenReturn(false);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(roleService.findRole((Integer) any())).thenReturn(new Role("Role Name"));
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        User user = mock(User.class);
        when(user.getEmail()).thenReturn("jane.doe@example.org");
        when(user.getName()).thenReturn("Name");
        when(user.getPassword()).thenReturn("iloveyou");
        when(user.getTelephoneNumber()).thenReturn("4105551212");
        ArrayList<Role> roleList = new ArrayList<>();
        when(user.getRoles()).thenReturn(roleList);
        doNothing().when(user).addRole((Role) any());
        user.addRole(new Role("Self-registration, Admin"));
        ResponseEntity<?> actualCreateUserResult = userService.createUser(user);
        assertTrue(actualCreateUserResult.hasBody());
        assertEquals(200, actualCreateUserResult.getStatusCodeValue());
        assertTrue(actualCreateUserResult.getHeaders().isEmpty());
        assertTrue(((User) actualCreateUserResult.getBody()).getIsVerified());
        assertTrue(((User) actualCreateUserResult.getBody()).isAccountNonExpired());
        assertEquals("Self-registration, Admin", ((User) actualCreateUserResult.getBody()).getVerifiedBy());
        assertEquals("jane.doe@example.org", ((User) actualCreateUserResult.getBody()).getUsername());
        assertEquals("4105551212", ((User) actualCreateUserResult.getBody()).getTelephoneNumber());
        assertEquals(roleList, ((User) actualCreateUserResult.getBody()).getRoles());
        assertEquals("secret", ((User) actualCreateUserResult.getBody()).getPassword());
        assertEquals("Name", ((User) actualCreateUserResult.getBody()).getName());
        assertTrue(((User) actualCreateUserResult.getBody()).getIsFullyVerified());
        assertEquals("jane.doe@example.org", ((User) actualCreateUserResult.getBody()).getEmail());
        assertTrue(((User) actualCreateUserResult.getBody()).isEnabled());
        assertTrue(((User) actualCreateUserResult.getBody()).isCredentialsNonExpired());
        assertTrue(((User) actualCreateUserResult.getBody()).isAccountNonLocked());
        verify(userRepository).existsByEmail((String) any());
        verify(userRepository).save((User) any());
        verify(passwordEncoder).encode((CharSequence) any());
        verify(user, atLeast(1)).getEmail();
        verify(user).getName();
        verify(user).getPassword();
        verify(user).getTelephoneNumber();
        verify(user).getRoles();
        verify(user).addRole((Role) any());
    }

    /**
     * Method under test: {@link UserService#verifyVendor(User, Long, User)}
     */
    @Test
    void testVerifyVendor() throws UserNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R011 Sandboxing policy violation.
        //   Diffblue Cover ran code in your project that tried
        //     to access the network.
        //   Diffblue Cover's default sandboxing policy disallows this in order to prevent
        //   your code from damaging your system environment.
        //   See https://diff.blue/R011 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        RoleService roleService = new RoleService(mock(RoleRepository.class));
        UserService userService = new UserService(userRepository, passwordEncoder, roleService,
                new JwtService(mock(UserRepository.class)));
        assertThrows(UserNotFoundException.class, () -> userService.verifyVendor(new User(), 1L, null));
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link UserService#verifyVendor(User, Long, User)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testVerifyVendor2() throws UserNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R011 Sandboxing policy violation.
        //   Diffblue Cover ran code in your project that tried
        //     to access the network.
        //   Diffblue Cover's default sandboxing policy disallows this in order to prevent
        //   your code from damaging your system environment.
        //   See https://diff.blue/R011 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Optional.isEmpty()" because "vendorToBeVerified" is null
        //       at com.cs490.shoppingcart.administrationmodule.service.UserService.verifyVendor(UserService.java:140)
        //   In order to prevent verifyVendor(User, Long, User)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   verifyVendor(User, Long, User).
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById((Long) any())).thenReturn(null);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        RoleService roleService = new RoleService(mock(RoleRepository.class));
        UserService userService = new UserService(userRepository, passwordEncoder, roleService,
                new JwtService(mock(UserRepository.class)));
        userService.verifyVendor(new User(), 1L, null);
    }

    /**
     * Method under test: {@link UserService#verifyVendor(User, Long, User)}
     */
    @Test
    void testVerifyVendor3() throws UserNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R011 Sandboxing policy violation.
        //   Diffblue Cover ran code in your project that tried
        //     to access the network.
        //   Diffblue Cover's default sandboxing policy disallows this in order to prevent
        //   your code from damaging your system environment.
        //   See https://diff.blue/R011 to resolve this issue.

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
     * Method under test: {@link UserService#verifyVendor(User, Long, User)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testVerifyVendor4() throws UserNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R011 Sandboxing policy violation.
        //   Diffblue Cover ran code in your project that tried
        //     to access the network.
        //   Diffblue Cover's default sandboxing policy disallows this in order to prevent
        //   your code from damaging your system environment.
        //   See https://diff.blue/R011 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R011 Sandboxing policy violation.
        //   Diffblue Cover ran code in your project that tried
        //     to access the network.
        //   Diffblue Cover's default sandboxing policy disallows this in order to prevent
        //   your code from damaging your system environment.
        //   See https://diff.blue/R011 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        RoleService roleService = new RoleService(mock(RoleRepository.class));
        UserService userService = new UserService(userRepository, passwordEncoder, roleService,
                new JwtService(mock(UserRepository.class)));
        User vendor = new User();
        userService.verifyVendor(vendor, 1L, new User());
    }

    /**
     * Method under test: {@link UserService#verifyVendor(User, Long, User)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testVerifyVendor5() throws UserNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R011 Sandboxing policy violation.
        //   Diffblue Cover ran code in your project that tried
        //     to access the network.
        //   Diffblue Cover's default sandboxing policy disallows this in order to prevent
        //   your code from damaging your system environment.
        //   See https://diff.blue/R011 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R011 Sandboxing policy violation.
        //   Diffblue Cover ran code in your project that tried
        //     to access the network.
        //   Diffblue Cover's default sandboxing policy disallows this in order to prevent
        //   your code from damaging your system environment.
        //   See https://diff.blue/R011 to resolve this issue.

        User user = mock(User.class);
        when(user.getNotificationRequest((String) any())).thenReturn(new NotificationRequest());
        doNothing().when(user).setIsVerified((Boolean) any());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setUsername((String) any());
        doNothing().when(user).setVerifiedBy((String) any());
        Optional<User> ofResult = Optional.of(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        RoleService roleService = new RoleService(mock(RoleRepository.class));
        UserService userService = new UserService(userRepository, passwordEncoder, roleService,
                new JwtService(mock(UserRepository.class)));
        User vendor = new User();
        userService.verifyVendor(vendor, 1L, new User());
    }

    /**
     * Method under test: {@link UserService#verifyVendor(User, Long, User)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testVerifyVendor6() throws UserNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R011 Sandboxing policy violation.
        //   Diffblue Cover ran code in your project that tried
        //     to access the network.
        //   Diffblue Cover's default sandboxing policy disallows this in order to prevent
        //   your code from damaging your system environment.
        //   See https://diff.blue/R011 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.security.crypto.password.PasswordEncoder.encode(java.lang.CharSequence)" because "this.passwordEncoder" is null
        //       at com.cs490.shoppingcart.administrationmodule.service.UserService.verifyVendor(UserService.java:149)
        //   In order to prevent verifyVendor(User, Long, User)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   verifyVendor(User, Long, User).
        //   See https://diff.blue/R013 to resolve this issue.

        User user = mock(User.class);
        when(user.getNotificationRequest((String) any())).thenReturn(new NotificationRequest());
        doNothing().when(user).setIsVerified((Boolean) any());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setUsername((String) any());
        doNothing().when(user).setVerifiedBy((String) any());
        Optional<User> ofResult = Optional.of(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        RoleService roleService = new RoleService(mock(RoleRepository.class));
        UserService userService = new UserService(userRepository, null, roleService,
                new JwtService(mock(UserRepository.class)));
        User vendor = new User();
        userService.verifyVendor(vendor, 1L, new User());
    }

    /**
     * Method under test: {@link UserService#verifyVendor(User, Long, User)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testVerifyVendor7() throws UserNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R011 Sandboxing policy violation.
        //   Diffblue Cover ran code in your project that tried
        //     to access the network.
        //   Diffblue Cover's default sandboxing policy disallows this in order to prevent
        //   your code from damaging your system environment.
        //   See https://diff.blue/R011 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R011 Sandboxing policy violation.
        //   Diffblue Cover ran code in your project that tried
        //     to access the network.
        //   Diffblue Cover's default sandboxing policy disallows this in order to prevent
        //   your code from damaging your system environment.
        //   See https://diff.blue/R011 to resolve this issue.

        User user = mock(User.class);
        when(user.getNotificationRequest((String) any())).thenReturn(new NotificationRequest());
        doNothing().when(user).setIsVerified((Boolean) any());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setUsername((String) any());
        doNothing().when(user).setVerifiedBy((String) any());
        Optional<User> ofResult = Optional.of(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        RoleService roleService = new RoleService(mock(RoleRepository.class));
        UserService userService = new UserService(userRepository, passwordEncoder, roleService,
                new JwtService(mock(UserRepository.class)));
        User vendor = new User();
        User user1 = mock(User.class);
        when(user1.getName()).thenReturn("Name");
        userService.verifyVendor(vendor, 1L, user1);
    }

    /**
     * Method under test: {@link UserService#verifyVendor(User, Long, User)}
     */
    @Test
    void testVerifyVendor8() throws UserNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R011 Sandboxing policy violation.
        //   Diffblue Cover ran code in your project that tried
        //     to access the network.
        //   Diffblue Cover's default sandboxing policy disallows this in order to prevent
        //   your code from damaging your system environment.
        //   See https://diff.blue/R011 to resolve this issue.

        User user = mock(User.class);
        when(user.getNotificationRequest((String) any())).thenReturn(new NotificationRequest());
        doNothing().when(user).setIsVerified((Boolean) any());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setUsername((String) any());
        doNothing().when(user).setVerifiedBy((String) any());
        Optional<User> ofResult = Optional.of(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        RoleService roleService = new RoleService(mock(RoleRepository.class));
        UserService userService = new UserService(userRepository, passwordEncoder, roleService,
                new JwtService(mock(UserRepository.class)));
        User vendor = new User();
        User user1 = mock(User.class);
        when(user1.getName()).thenThrow(new InvalidCredentialsException("An error occurred"));
        assertThrows(InvalidCredentialsException.class, () -> userService.verifyVendor(vendor, 1L, user1));
        verify(userRepository).findById((Long) any());
        verify(user1).getName();
    }

    /**
     * Method under test: {@link UserService#fullyVerifyVendor(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFullyVerifyVendor() throws NotVerifiedException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Boolean.booleanValue()" because the return value of "com.cs490.shoppingcart.administrationmodule.model.User.getIsVerified()" is null
        //       at com.cs490.shoppingcart.administrationmodule.service.UserService.fullyVerifyVendor(UserService.java:161)
        //   In order to prevent fullyVerifyVendor(Long)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   fullyVerifyVendor(Long).
        //   See https://diff.blue/R013 to resolve this issue.

        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        userService.fullyVerifyVendor(123L);
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
     * Method under test: {@link UserService#fullyVerifyVendor(Long)}
     */
    @Test
    void testFullyVerifyVendor3() throws NotVerifiedException {
        User user = new User();
        user.setIsVerified(true);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(jwtService.refreshToken((String) any())).thenThrow(new InvalidCredentialsException("An error occurred"));
        assertThrows(InvalidCredentialsException.class, () -> userService.fullyVerifyVendor(123L));
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
        verify(jwtService).refreshToken((String) any());
    }

    /**
     * Method under test: {@link UserService#fullyVerifyVendor(Long)}
     */
    @Test
    void testFullyVerifyVendor4() throws NotVerifiedException {
        User user = mock(User.class);
        when(user.getName()).thenReturn("Name");
        when(user.getUsername()).thenReturn("janedoe");
        doNothing().when(user).setIsFullyVerified((Boolean) any());
        when(user.getIsVerified()).thenReturn(true);
        doNothing().when(user).setIsVerified((Boolean) any());
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
        verify(user).getIsVerified();
        verify(user, atLeast(1)).getName();
        verify(user).getUsername();
        verify(user).setIsFullyVerified((Boolean) any());
        verify(user).setIsVerified((Boolean) any());
        verify(jwtService).refreshToken((String) any());
    }

    /**
     * Method under test: {@link UserService#fullyVerifyVendor(Long)}
     */
    @Test
    void testFullyVerifyVendor5() throws NotVerifiedException {
        User user = mock(User.class);
        when(user.getName()).thenThrow(new InvalidCredentialsException("An error occurred"));
        when(user.getUsername()).thenThrow(new InvalidCredentialsException("An error occurred"));
        doThrow(new InvalidCredentialsException("An error occurred")).when(user).setIsFullyVerified((Boolean) any());
        when(user.getIsVerified()).thenReturn(true);
        doNothing().when(user).setIsVerified((Boolean) any());
        user.setIsVerified(true);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(jwtService.refreshToken((String) any())).thenReturn("ABC123");
        assertThrows(InvalidCredentialsException.class, () -> userService.fullyVerifyVendor(123L));
        verify(userRepository).findById((Long) any());
        verify(user).getIsVerified();
        verify(user).setIsFullyVerified((Boolean) any());
        verify(user).setIsVerified((Boolean) any());
    }

    /**
     * Method under test: {@link UserService#fullyVerifyVendor(Long)}
     */
    @Test
    void testFullyVerifyVendor6() throws NotVerifiedException {
        User user = mock(User.class);
        when(user.getName()).thenReturn("Name");
        when(user.getUsername()).thenReturn("janedoe");
        doNothing().when(user).setIsFullyVerified((Boolean) any());
        when(user.getIsVerified()).thenReturn(false);
        doNothing().when(user).setIsVerified((Boolean) any());
        user.setIsVerified(true);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        when(jwtService.refreshToken((String) any())).thenReturn("ABC123");
        assertThrows(NotVerifiedException.class, () -> userService.fullyVerifyVendor(123L));
        verify(userRepository).findById((Long) any());
        verify(user).getIsVerified();
        verify(user).setIsVerified((Boolean) any());
    }

    /**
     * Method under test: {@link UserService#fullyVerifyVendor(Long)}
     */
    @Test
    void testFullyVerifyVendor7() throws NotVerifiedException {
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());
        User user = mock(User.class);
        when(user.getName()).thenReturn("Name");
        when(user.getUsername()).thenReturn("janedoe");
        doNothing().when(user).setIsFullyVerified((Boolean) any());
        when(user.getIsVerified()).thenReturn(true);
        doNothing().when(user).setIsVerified((Boolean) any());
        user.setIsVerified(true);
        when(jwtService.refreshToken((String) any())).thenReturn("ABC123");
        assertThrows(NotVerifiedException.class, () -> userService.fullyVerifyVendor(123L));
        verify(userRepository).findById((Long) any());
        verify(user).setIsVerified((Boolean) any());
    }

    /**
     * Method under test: {@link UserService#updateUserById(Long, User)}
     */
    @Test
    void testUpdateUserById() throws UserNotFoundException {
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        User user = new User();
        ResponseEntity<User> actualUpdateUserByIdResult = userService.updateUserById(123L, user);
        assertEquals(user, actualUpdateUserByIdResult.getBody());
        assertTrue(actualUpdateUserByIdResult.getHeaders().isEmpty());
        assertEquals(200, actualUpdateUserByIdResult.getStatusCodeValue());
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link UserService#updateUserById(Long, User)}
     */
    @Test
    void testUpdateUserById2() throws UserNotFoundException {
        when(userRepository.save((User) any())).thenThrow(new InvalidCredentialsException("An error occurred"));
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        assertThrows(InvalidCredentialsException.class, () -> userService.updateUserById(123L, new User()));
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link UserService#updateUserById(Long, User)}
     */
    @Test
    void testUpdateUserById3() throws UserNotFoundException {
        User user = mock(User.class);
        when(user.getUserId()).thenReturn(123L);
        doNothing().when(user).setAccountNonExpired(anyBoolean());
        doNothing().when(user).setAccountNonLocked(anyBoolean());
        doNothing().when(user).setAddress((String) any());
        doNothing().when(user).setCredentialsNonExpired(anyBoolean());
        doNothing().when(user).setEmail((String) any());
        doNothing().when(user).setEnabled(anyBoolean());
        doNothing().when(user).setName((String) any());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setTelephoneNumber((String) any());
        doNothing().when(user).setUserId((Long) any());
        doNothing().when(user).setVerifiedBy((String) any());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        User user1 = new User();
        ResponseEntity<User> actualUpdateUserByIdResult = userService.updateUserById(123L, user1);
        assertEquals(user1, actualUpdateUserByIdResult.getBody());
        assertTrue(actualUpdateUserByIdResult.getHeaders().isEmpty());
        assertEquals(200, actualUpdateUserByIdResult.getStatusCodeValue());
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
        verify(user).getUserId();
        verify(user).setAccountNonExpired(anyBoolean());
        verify(user).setAccountNonLocked(anyBoolean());
        verify(user).setAddress((String) any());
        verify(user).setCredentialsNonExpired(anyBoolean());
        verify(user).setEmail((String) any());
        verify(user).setEnabled(anyBoolean());
        verify(user).setName((String) any());
        verify(user).setPassword((String) any());
        verify(user).setTelephoneNumber((String) any());
        verify(user).setUserId((Long) any());
        verify(user).setVerifiedBy((String) any());
    }

    /**
     * Method under test: {@link UserService#updateUserById(Long, User)}
     */
    @Test
    void testUpdateUserById4() throws UserNotFoundException {
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.updateUserById(123L, new User()));
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link UserService#updateUserById(Long, User)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateUserById5() throws UserNotFoundException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.cs490.shoppingcart.administrationmodule.model.User.getName()" because "updatedUser" is null
        //       at com.cs490.shoppingcart.administrationmodule.service.UserService.updateUserById(UserService.java:179)
        //   In order to prevent updateUserById(Long, User)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   updateUserById(Long, User).
        //   See https://diff.blue/R013 to resolve this issue.

        User user = mock(User.class);
        when(user.getUserId()).thenReturn(123L);
        doNothing().when(user).setAccountNonExpired(anyBoolean());
        doNothing().when(user).setAccountNonLocked(anyBoolean());
        doNothing().when(user).setAddress((String) any());
        doNothing().when(user).setCredentialsNonExpired(anyBoolean());
        doNothing().when(user).setEmail((String) any());
        doNothing().when(user).setEnabled(anyBoolean());
        doNothing().when(user).setName((String) any());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setTelephoneNumber((String) any());
        doNothing().when(user).setUserId((Long) any());
        doNothing().when(user).setVerifiedBy((String) any());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        userService.updateUserById(123L, null);
    }

    /**
     * Method under test: {@link UserService#updateUserById(Long, User)}
     */
    @Test
    void testUpdateUserById6() throws UserNotFoundException {
        User user = mock(User.class);
        when(user.getUserId()).thenReturn(123L);
        doNothing().when(user).setAccountNonExpired(anyBoolean());
        doNothing().when(user).setAccountNonLocked(anyBoolean());
        doNothing().when(user).setAddress((String) any());
        doNothing().when(user).setCredentialsNonExpired(anyBoolean());
        doNothing().when(user).setEmail((String) any());
        doNothing().when(user).setEnabled(anyBoolean());
        doNothing().when(user).setName((String) any());
        doNothing().when(user).setPassword((String) any());
        doNothing().when(user).setTelephoneNumber((String) any());
        doNothing().when(user).setUserId((Long) any());
        doNothing().when(user).setVerifiedBy((String) any());
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        User user1 = mock(User.class);
        when(user1.isAccountNonExpired()).thenReturn(true);
        when(user1.isAccountNonLocked()).thenReturn(true);
        when(user1.isCredentialsNonExpired()).thenReturn(true);
        when(user1.isEnabled()).thenReturn(true);
        when(user1.getAddress()).thenReturn("42 Main St");
        when(user1.getEmail()).thenReturn("jane.doe@example.org");
        when(user1.getName()).thenReturn("Name");
        when(user1.getPassword()).thenReturn("iloveyou");
        when(user1.getTelephoneNumber()).thenReturn("4105551212");
        when(user1.getVerifiedBy()).thenReturn("Verified By");
        ResponseEntity<User> actualUpdateUserByIdResult = userService.updateUserById(123L, user1);
        assertTrue(actualUpdateUserByIdResult.hasBody());
        assertTrue(actualUpdateUserByIdResult.getHeaders().isEmpty());
        assertEquals(200, actualUpdateUserByIdResult.getStatusCodeValue());
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
        verify(user).getUserId();
        verify(user).setAccountNonExpired(anyBoolean());
        verify(user).setAccountNonLocked(anyBoolean());
        verify(user).setAddress((String) any());
        verify(user).setCredentialsNonExpired(anyBoolean());
        verify(user).setEmail((String) any());
        verify(user).setEnabled(anyBoolean());
        verify(user).setName((String) any());
        verify(user).setPassword((String) any());
        verify(user).setTelephoneNumber((String) any());
        verify(user).setUserId((Long) any());
        verify(user).setVerifiedBy((String) any());
        verify(user1).isAccountNonExpired();
        verify(user1).isAccountNonLocked();
        verify(user1).isCredentialsNonExpired();
        verify(user1).isEnabled();
        verify(user1).getAddress();
        verify(user1).getEmail();
        verify(user1).getName();
        verify(user1).getPassword();
        verify(user1).getTelephoneNumber();
        verify(user1).getVerifiedBy();
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

    /**
     * Method under test: {@link UserService#findUser(Long)}
     */
    @Test
    void testFindUser2() {
        when(userRepository.findById((Long) any())).thenThrow(new InvalidCredentialsException("An error occurred"));
        assertThrows(InvalidCredentialsException.class, () -> userService.findUser(123L));
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link UserService#deleteUser(Long)}
     */
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
     * Method under test: {@link UserService#deleteUser(Long)}
     */
    @Test
    void testDeleteUser2() {
        doThrow(new InvalidCredentialsException("An error occurred")).when(userRepository).delete((User) any());
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        assertThrows(InvalidCredentialsException.class, () -> userService.deleteUser(123L));
        verify(userRepository).findById((Long) any());
        verify(userRepository).delete((User) any());
    }

    /**
     * Method under test: {@link UserService#deleteUser(Long)}
     */
    @Test
    void testDeleteUser3() {
        doNothing().when(userRepository).delete((User) any());
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());
        ResponseEntity<?> actualDeleteUserResult = userService.deleteUser(123L);
        assertEquals("you can not delete this user because does not exist", actualDeleteUserResult.getBody());
        assertEquals(404, actualDeleteUserResult.getStatusCodeValue());
        assertTrue(actualDeleteUserResult.getHeaders().isEmpty());
        verify(userRepository).findById((Long) any());
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
     * Method under test: {@link UserService#allUsers()}
     */
    @Test
    void testAllUsers2() {
        when(userRepository.findAll()).thenThrow(new InvalidCredentialsException("An error occurred"));
        assertThrows(InvalidCredentialsException.class, () -> userService.allUsers());
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
     * Method under test: {@link UserService#generateToken(String)}
     */
    @Test
    void testGenerateToken2() throws InvalidCredentialsException {
        when(jwtService.generateToken((String) any())).thenThrow(new InvalidCredentialsException("An error occurred"));
        assertThrows(InvalidCredentialsException.class, () -> userService.generateToken("janedoe"));
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

    /**
     * Method under test: {@link UserService#validateToken(String)}
     */
    @Test
    void testValidateToken2() {
        when(jwtService.validateToken((String) any())).thenThrow(new InvalidCredentialsException("An error occurred"));
        assertThrows(InvalidCredentialsException.class, () -> userService.validateToken("ABC123"));
        verify(jwtService).validateToken((String) any());
    }
}

