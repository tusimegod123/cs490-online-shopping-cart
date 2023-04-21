package com.cs490.shoppingcart.administrationmodule.service;

import com.cs490.shoppingcart.administrationmodule.dto.NotificationRequest;
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
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.cs490.shoppingcart.administrationmodule.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private static final Logger logger =
            LoggerFactory.getLogger(UserService.class);

    private final JwtService jwtService;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.jwtService = jwtService;
    }

//    @Value("${SECRET}")
//    public String SECRET;

    public  final  String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";



    public String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return RandomStringUtils.random(length, characters);
    }
    public String generateRandomUsername(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return RandomStringUtils.random(length, characters);
    }

    public  String randomPassword = generateRandomPassword(8);
    public  String passwordBeforeEncoded = randomPassword;


    public User createUser(User userDto) throws IllegalArgumentException {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setTelephoneNumber(userDto.getTelephoneNumber());

        List<Role> roles = userDto.getRoles().stream().map(roleDto -> {
            Role role = roleService.findRole(roleDto.getRoleId());
            role.setRoleId(roleDto.getRoleId());
            return role;
        }).collect(Collectors.toList());
        // set default username and password for vendors
        if (roles.stream().anyMatch(role -> "VENDOR".equals(role.getRoleName()))) {
            user.setIsVerified(false);
        } else if (roles.stream().anyMatch(role -> "GUEST".equals(role.getRoleName()))){
            user.setIsVerified(false);
            user.setUsername(null);
        } else if (roles.stream().anyMatch(role -> "REGISTERED_USER".equals(role.getRoleName()))){
            user.setIsVerified(true);
            user.setUsername(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setIsFullyVerified(true);
            user.setVerifiedBy("Self registration, Customer");
        }
        else {
            user.setUsername(userDto.getEmail());
            user.setIsVerified(true);
            user.setIsFullyVerified(true);
            user.setVerifiedBy("Self registration, Admin");
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }
    private String sendPasswordToUser(String password) {
        return password;
    }

    public String getPassword(){
        return randomPassword;
    }

    public ResponseEntity<?> verifyVendor(User vendor, Long vendorId, @AuthenticationPrincipal User admin) throws UserNotFoundException {
        String randomUsername = generateRandomUsername(6);
        String randomPassword = generateRandomPassword(8);
        vendor.setUsername(randomUsername);
        vendor.setPassword(randomPassword);

        Optional<User> vendorToBeVerified = userRepository.findById(vendorId);
        if (!vendorToBeVerified.isPresent()) {
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

        return ResponseEntity.ok(vendorToBeVerified.get());
    }


    public User fullyVerifyVendor(Long vendorId) throws NotVerifiedException {
        User vendorToBeVerified = userRepository.findById(vendorId).orElseThrow(() -> new EntityNotFoundException("Vendor not found with id: " + vendorId));
        if (vendorToBeVerified.getIsVerified() == false) {
            throw new NotVerifiedException("Sorry " +vendorToBeVerified.getName() + "  is not yet verified, contact the admin");
        } else {
            vendorToBeVerified.setIsFullyVerified(true);
            return userRepository.save(vendorToBeVerified);
        }
    }

    public ResponseEntity<?> updateUserDetails(User user, Long id){
        try{
            User userTobeUpdated = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
            if (userTobeUpdated == null){
                throw new UserNotFoundException(" user does not exist");
            }else {
                userTobeUpdated.setPassword(user.getPassword());
                userTobeUpdated.setEmail(user.getEmail());
                userTobeUpdated.setTelephoneNumber(user.getTelephoneNumber());
                userTobeUpdated.setRoles(user.getRoles());
                userTobeUpdated.setEnabled(user.isEnabled());
                userTobeUpdated.setAccountNonExpired(user.isAccountNonExpired());
                userTobeUpdated.setAccountNonLocked(user.isAccountNonLocked());
                return  ResponseEntity.ok(userRepository.save(userTobeUpdated));
            }
        } catch (UserNotFoundException userNotFoundException) {
            String errorJson = "{\"Sorry\":\"" + userNotFoundException.getMessage() + "\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorJson);
        }
    }

public ResponseEntity<?> findUser(Long id){
    try {
           Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new UserNotFoundException("Sorry this does not exist in our system");
        }
        return ResponseEntity.ok(user);
    } catch (UserNotFoundException e) {
        String errorJson = "{\"Sorry\":\"" + e.getMessage() + "\"}";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorJson);
    }
}



    public List<User> allUsers(){
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
        String url = "http://localhost:8088/notification-service/notification/email";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<NotificationRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
           // String decryptedPassword = passwordEncoder().decode(request.getPassword());

            logger.info("userId: " + request.getUserId());
            logger.info("emailType: " + request.getEmailType());
//            logger.info("password: " + request.setPassword(randomPassword));
            request.setPassword(passwordBeforeEncoded);
            System.out.println(passwordBeforeEncoded);
            logger.info("password: "+request.getPassword());
            logger.info("message: " + request.getMessage());

        } else {
            logger.error("Failed ");
        }
    }


}
