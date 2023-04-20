package com.cs490.shoppingcart.administrationmodule.service;

import com.cs490.shoppingcart.administrationmodule.exception.InvalidCredentialsException;
import com.cs490.shoppingcart.administrationmodule.exception.NotVerifiedException;
import com.cs490.shoppingcart.administrationmodule.exception.UserNotFoundException;
import com.cs490.shoppingcart.administrationmodule.model.Role;
import com.cs490.shoppingcart.administrationmodule.model.User;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang.RandomStringUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import com.cs490.shoppingcart.administrationmodule.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

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

    public User createUser(User userDto) throws IllegalArgumentException {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setTelephoneNumber(userDto.getTelephoneNumber());

        Set<Role> roles = userDto.getRoles().stream().map(roleDto -> {
            Role role = roleService.findRole(roleDto.getRoleId());
            role.setRoleId(roleDto.getRoleId());
            return role;
        }).collect(Collectors.toSet());

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
    private void sendPasswordToUser(User vendor, String username, String password) {
        // code to send password to user via email or SMS
        System.out.println("Hello " + vendor.getName() + " Your  username for logging in is " + username  + " and your password is " + password);
    }

public ResponseEntity<?> verifyVendor(User vendor, Long vendorId, @AuthenticationPrincipal User admin) {
    User vendorToBeVerified = userRepository.findById(vendorId).orElseThrow(() -> new EntityNotFoundException("Vendor not found with id: " + vendorId));

    String randomUsername = generateRandomUsername(6);
    String randomPassword = generateRandomPassword(8);

    vendor.setUsername(randomUsername);
    vendor.setPassword(randomPassword);
    try {
        if (admin == null) {
            throw new UserNotFoundException("  you not authorised to verify this vendor, Kindly login as an Admin");
        }
        vendorToBeVerified.setVerifiedBy(admin.getName());
        vendorToBeVerified.setIsVerified(true);
        vendorToBeVerified.setUsername(vendor.getUsername());
        vendorToBeVerified.setPassword(passwordEncoder.encode(randomPassword));

        sendPasswordToUser(vendor, randomUsername, randomPassword);
        System.out.println("Vendor verified by: " + admin.getName());
        //return userRepository.save(vendorToBeVerified);
        return  ResponseEntity.ok(userRepository.save(vendorToBeVerified));
    } catch (UserNotFoundException e) {
        String errorJson = "{\"Sorry\":\"" + e.getMessage() + "\"}";
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorJson);
    }
}

    public User fullyVerifyVendor(User vendor, Long vendorId, @AuthenticationPrincipal User admin) throws NotVerifiedException {
        User vendorToBeVerified = userRepository.findById(vendorId).orElseThrow(() -> new EntityNotFoundException("Vendor not found with id: " + vendorId));
        if (vendorToBeVerified.getIsVerified() == false) {
            throw new NotVerifiedException("Sorry " +vendorToBeVerified.getName() + "  is not yet verified, contact the admin");
        } else {
            vendorToBeVerified.setIsFullyVerified(true);
            System.out.println("Vendor verified by: " + admin.getName());
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

}
