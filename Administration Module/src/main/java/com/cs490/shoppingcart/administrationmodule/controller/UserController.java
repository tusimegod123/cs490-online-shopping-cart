package com.cs490.shoppingcart.administrationmodule.controller;


import com.cs490.shoppingcart.administrationmodule.dto.AuthRequest;
import com.cs490.shoppingcart.administrationmodule.dto.UserDto;
import com.cs490.shoppingcart.administrationmodule.exception.InvalidCredentialsException;
import com.cs490.shoppingcart.administrationmodule.exception.UserNotFoundException;
import com.cs490.shoppingcart.administrationmodule.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.cs490.shoppingcart.administrationmodule.service.UserService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final  PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, ModelMapper modelMapper) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
            User user = new User(userDto.getName(), userDto.getEmail(), userDto.getPassword(),
                    userDto.getTelephoneNumber(), userDto.getUsername(), userDto.getRoles());
            return ResponseEntity.ok(userService.createUser(user)).getBody();
    }
     @PostMapping("/login")
        public ResponseEntity<?> getToken(@RequestBody AuthRequest authRequest) {
            try {
                Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
                if (authenticate.isAuthenticated()) {
                    return ResponseEntity.ok(userService.generateToken(authRequest.getUsername()));
//                    return userService.generateToken(authRequest.getUsername());
                } else {
                    throw new InvalidCredentialsException("Invalid username or password");
                }
            } catch (AuthenticationException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
            }
     }

        @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        userService.validateToken(token);
        return "Token is valid";
    }

@PutMapping("/{userId}")
public ResponseEntity<?> updateUserById(@PathVariable Long userId, @RequestBody User updatedUser)  {
    ResponseEntity<User> response = null;
    try {
        response = userService.updateUserById(userId, updatedUser);
    } catch (UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    return response;
}

@PutMapping(value = "/vendor/verify/{id}")
public ResponseEntity<?> verifyVendor(@RequestBody User vendor, @PathVariable Long id, @AuthenticationPrincipal User admin) {
    try {
        return userService.verifyVendor(vendor, id, admin);
    } catch (UserNotFoundException e) {
        String errorJson = "{\"error\":\"" + e.getMessage() + "\"}";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorJson);
    }
}

@PutMapping(value = "/vendor/fullyVerify/{id}")
public ResponseEntity<String> fullyVerifyVendor(@PathVariable Long id) {

    try {
        return  userService.fullyVerifyVendor(id);
    } catch (Exception e) {
        String errorJson = "{\"error\":\"" + e.getMessage() + "\"}";
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorJson);
    }
}

    @GetMapping("/{id}")
    public ResponseEntity<?> user(@PathVariable Long id){
        return userService.findUser(id);
    }

@GetMapping
public ResponseEntity<List<UserDto>>getUsers() {
    List<User> users = userService.allUsers();
    List<UserDto> userDtos = new ArrayList<>();
    for (User user : users) {
        UserDto userDto = new UserDto(user.getUserId(), user.getName(), user.getEmail(),
                user.getTelephoneNumber(), user.getUsername(), user.getIsVerified(),
                user.getIsFullyVerified(), user.getVerifiedBy(),
                user.getRoles());
        userDtos.add(userDto);
    }
    return ResponseEntity.ok(userDtos);
}

    @DeleteMapping("/{userId}")
public ResponseEntity<?> deleteUser(@PathVariable Long userId){

      return ResponseEntity.ok(userService.deleteUser(userId)).getBody();
}

//    @GetMapping("/who")
//    public ResponseEntity<User> getUser(Authentication authentication) {
//        User user = (User) authentication.getPrincipal();
//        return ResponseEntity.ok(user);
//    }
}
