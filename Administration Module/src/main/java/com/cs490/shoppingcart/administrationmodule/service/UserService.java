package com.cs490.shoppingcart.administrationmodule.service;

import com.cs490.shoppingcart.administrationmodule.dto.NotificationRequest;
import com.cs490.shoppingcart.administrationmodule.exception.EmailExistsException;
import com.cs490.shoppingcart.administrationmodule.exception.InvalidCredentialsException;
import com.cs490.shoppingcart.administrationmodule.exception.NotVerifiedException;
import com.cs490.shoppingcart.administrationmodule.exception.UserNotFoundException;
import com.cs490.shoppingcart.administrationmodule.model.Role;
import com.cs490.shoppingcart.administrationmodule.model.User;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import com.cs490.shoppingcart.administrationmodule.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.jwtService = jwtService;
    }

    // @Value("${SECRET}")
    // public String SECRET;

    public final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return RandomStringUtils.random(length, characters);
    }

    public String generateRandomUsername(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return RandomStringUtils.random(length, characters);
    }
    public String randomPassword = generateRandomPassword(8);
    public String passwordBeforeEncoded = randomPassword;

public ResponseEntity<?> createUser(User userDto) {
    try {
        // Check if user with this email already exists
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailExistsException("A user with this email already exists");
        }

        // Create new user
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setTelephoneNumber(userDto.getTelephoneNumber());

        // Set roles
        List<Role> roles = userDto.getRoles().stream().map(roleDto -> {
            Role role = roleService.findRole(roleDto.getRoleId());
            role.setRoleId(roleDto.getRoleId());
            return role;
        }).collect(Collectors.toList());
        user.setRoles(roles);

        // Set username, password, and verification status based on roles
        if (roles.stream().anyMatch(role -> "VENDOR".equals(role.getRoleName()))) {
            user.setIsVerified(false);
            user.setUsername(null);
            logger.info(user.getName() + " has  successfully registered as a VENDOR PENDING verification ");
        } else if (roles.stream().anyMatch(role -> "GUEST".equals(role.getRoleName()))) {
            user.setIsVerified(false);
            user.setUsername(null);
            logger.info(user.getName() + " placed an order as a GUEST user");
        } else if (roles.stream().anyMatch(role -> "REGISTERED_USER".equals(role.getRoleName()))) {
            user.setIsVerified(true);
            user.setUsername(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setIsFullyVerified(true);
            user.setVerifiedBy("Self-registration, Customer");
            logger.info(user.getName() + " has  successfully registered as a REGISTERED_USER ");
        } else {
            user.setUsername(userDto.getEmail());
            user.setIsVerified(true);
            user.setIsFullyVerified(true);
            user.setVerifiedBy("Self-registration, Admin");
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        // Save user to database
        userRepository.save(user);

        // Return success response
        return ResponseEntity.status(HttpStatus.OK).body(user);
    } catch (EmailExistsException e) {
        // Return error response with custom message
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
        // Return error response with generic message
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the user");
    }
}


    private String sendPasswordToUser(String password) {
        return password;
    }

    public String getPassword() {
        return randomPassword;
    }

    public ResponseEntity<?> verifyVendor(User vendor, Long vendorId, @AuthenticationPrincipal User admin)
            throws UserNotFoundException {
        String randomUsername = generateRandomUsername(6);
        String randomPassword = generateRandomPassword(8);
        vendor.setUsername(randomUsername);
        vendor.setPassword(randomPassword);

        Optional<User> vendorToBeVerified = userRepository.findById(vendorId);
        if (vendorToBeVerified.isEmpty()) {
            throw new UserNotFoundException("Sorry, this user does not exist.");
        }
        if (admin == null) {
            throw new UserNotFoundException("You are not authorized to verify this vendor. Please log in as an admin.");
        }
        vendorToBeVerified.get().setVerifiedBy(admin.getName());
        vendorToBeVerified.get().setIsVerified(true);
        vendorToBeVerified.get().setUsername(vendor.getUsername());
        vendorToBeVerified.get().setPassword(passwordEncoder.encode(randomPassword));
        userRepository.save(vendorToBeVerified.get());
        sendPasswordToUser(randomPassword);
        sendNotification(vendorToBeVerified.get().getNotificationRequest(randomPassword));
        logger.info(vendorToBeVerified.get().getName() + " has  been successfully Verified");
        return ResponseEntity.ok(vendorToBeVerified.get());
    }

public ResponseEntity<String> fullyVerifyVendor(Long vendorId) throws NotVerifiedException {
    Optional<User> optionalVendor = userRepository.findById(vendorId);
    User vendor = optionalVendor.orElseThrow(() -> new NotVerifiedException("Vendor not found"));

    if (!vendor.getIsVerified()) {
        throw new NotVerifiedException("Vendor is not verified yet, please contact the administrator");
    }
    vendor.setIsFullyVerified(true);
    userRepository.save(vendor);
    logger.info(vendor.getName() + " has  been is now fully verified");
    String refreshedToken = jwtService.refreshToken(vendor.getUsername());
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + refreshedToken);

    String successMessage = "Vendor " + vendor.getName() + " has been fully verified";
    return ResponseEntity.ok().headers(headers).body(successMessage);
}


    public ResponseEntity<User> updateUserById(Long userId, User updatedUser) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        user.setUserId(user.getUserId());
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setVerifiedBy(updatedUser.getVerifiedBy());
        user.setTelephoneNumber(updatedUser.getTelephoneNumber());
        user.setAddress(updatedUser.getAddress());
//        user.setRoles(updatedUser.getRoles());
        user.setAccountNonExpired(updatedUser.isAccountNonExpired());
        user.setAccountNonLocked(updatedUser.isAccountNonLocked());
        user.setCredentialsNonExpired(updatedUser.isCredentialsNonExpired());
        user.setEnabled(updatedUser.isEnabled());

        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    public ResponseEntity<?> findUser(Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isEmpty()) {
                throw new UserNotFoundException("Sorry this does not exist in our system");
            }
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            String errorJson = "{\"Sorry\":\"" + e.getMessage() + "\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorJson);
        }
    }

    public ResponseEntity<?> deleteUser(Long userId){
    Optional<User> userToBeDeleted = userRepository.findById(userId);
    try{
    if (userToBeDeleted.isEmpty()){
        throw new UserNotFoundException("you can not delete this user because does not exist");
    }else {
        userRepository.delete(userToBeDeleted.get());
        return ResponseEntity.status(HttpStatus.OK).body("User Successfully deleted");
    }
    } catch (UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    }
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public String generateToken(String username) throws InvalidCredentialsException {
        return jwtService.generateToken(username);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
    private void sendNotification(NotificationRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8088/notification-service/email";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<NotificationRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            logger.info("userId: " + request.getUserId());
            logger.info("emailType: " + request.getEmailType());
            request.setPassword(passwordBeforeEncoded);
            System.out.println(passwordBeforeEncoded);
            logger.info("password: " + request.getPassword());
            logger.info("message: " + request.getMessage());
        } else {
            logger.error("Failed ");
        }
    }

}
