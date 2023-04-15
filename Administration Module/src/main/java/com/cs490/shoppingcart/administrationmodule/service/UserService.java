package com.cs490.shoppingcart.administrationmodule.service;

import com.cs490.shoppingcart.administrationmodule.dto.UserDto;
import com.cs490.shoppingcart.administrationmodule.exception.InvalidCredentialsException;
import com.cs490.shoppingcart.administrationmodule.model.Role;
import com.cs490.shoppingcart.administrationmodule.model.User;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang.RandomStringUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import com.cs490.shoppingcart.administrationmodule.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Value("${SECRET}")
    public String SECRET;

    public String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return RandomStringUtils.random(length, characters);
    }
    public String generateRandomUsername(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return RandomStringUtils.random(length, characters);
    }

    public User createUser(UserDto userDto) throws IllegalArgumentException {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setTelephoneNumber(userDto.getTelephoneNumber());

        Set<Role> roles = userDto.getRoles().stream().map(roleDto -> {
            Role role = new Role();
            role.setRoleName(roleDto.getRoleName());
            return role;
        }).collect(Collectors.toSet());

        // set default username and password for vendors
        if (roles.stream().anyMatch(role -> "Vendor".equals(role.getRoleName()))) {
            user.setIsVerified(false);
        } else if (roles.stream().anyMatch(role -> "Guest".equals(role.getRoleName()))){
            user.setIsVerified(false);
            user.setUsername(null);
        }
        else {
            user.setUsername(userDto.getEmail());
            user.setIsVerified(true);
            user.setIsFullyVerified(true);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }
    private void sendPasswordToUser(User vendor, String username, String password) {
        // code to send password to user via email or SMS
        System.out.println("Hello " + vendor.getName() + " Your  username for logging in is " + username  + " and your password is " + password);
    }

public User verifyVendor(User vendor, Long vendorId, @AuthenticationPrincipal User admin) {
    User vendorToBeVerified = userRepository.findById(vendorId).orElseThrow(() -> new EntityNotFoundException("Vendor not found with id: " + vendorId));

    String randomUsername = generateRandomUsername(6);
    String randomPassword = generateRandomPassword(8);

    vendor.setUsername(randomUsername);
    vendor.setPassword(randomPassword);

    vendorToBeVerified.setVerifiedBy(admin.getName());
    vendorToBeVerified.setIsVerified(true);
    vendorToBeVerified.setUsername(vendor.getUsername());
    vendorToBeVerified.setPassword(passwordEncoder.encode(randomPassword));

    sendPasswordToUser(vendor, randomUsername, randomPassword);
    System.out.println("Vendor verified by: " + admin.getName());

    return userRepository.save(vendorToBeVerified);
}
    public User fullyVerifyVendor(User vendor, Long vendorId, @AuthenticationPrincipal User admin) {
        User vendorToBeVerified = userRepository.findById(vendorId).orElseThrow(() -> new EntityNotFoundException("Vendor not found with id: " + vendorId));
        vendorToBeVerified.setIsFullyVerified(true);
        System.out.println("Vendor verified by: " + admin.getName());
        return userRepository.save(vendorToBeVerified);
    }

    public User updateUserDetails(User user, Long id){
        User userTobeUpdated = userRepository.findById(id).get();
        userTobeUpdated.setPassword(user.getPassword());
        userTobeUpdated.setEmail(user.getEmail());
        userTobeUpdated.setTelephoneNumber(user.getTelephoneNumber());
        userTobeUpdated.setRoles(user.getRoles());
        userTobeUpdated.setEnabled(user.isEnabled());
        userTobeUpdated.setAccountNonExpired(user.isAccountNonExpired());
        userTobeUpdated.setAccountNonLocked(user.isAccountNonLocked());
        return  userRepository.save(userTobeUpdated);
    }
    public User findUser(Long id){
        return userRepository.findById(id).get();
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

}
