package com.cs490.shoppingcart.administrationmodule.controller;


import com.cs490.shoppingcart.administrationmodule.dto.AuthRequest;
import com.cs490.shoppingcart.administrationmodule.dto.UserDto;
import com.cs490.shoppingcart.administrationmodule.exception.InvalidCredentialsException;
import com.cs490.shoppingcart.administrationmodule.exception.NotVerifiedException;
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
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
            User user = new User(userDto.getName(), userDto.getEmail(), userDto.getPassword(),
                    userDto.getTelephoneNumber(), userDto.getUsername(), userDto.getRoles());
            User savedUser = userService.createUser(user);
            UserDto savedUserDto = mapUserToUserDto(savedUser);
            return ResponseEntity.created(URI.create("/users/" + savedUserDto.getUserId()))
                    .body(savedUserDto);
    }

    @PostMapping("/login")
    public String getToken(@RequestBody AuthRequest authRequest) throws InvalidCredentialsException {
        try {
                Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
                if (authenticate.isAuthenticated()) {
                     return userService.generateToken(authRequest.getUsername());
        } else {
                throw new InvalidCredentialsException("invalid access");
            }
            } catch (AuthenticationException e) {
            return e.getMessage();
        } catch (InvalidCredentialsException e) {
           return e.getMessage();
        }

    }
        @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        userService.validateToken(token);
        return "Token is valid";
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable Long id){
        return userService.updateUserDetails(user,id);
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
public ResponseEntity<?> fullyVerifyVendor( @PathVariable Long id) {
    try {
        User verifiedVendor = userService.fullyVerifyVendor(id);
        return ResponseEntity.ok().body(verifiedVendor);
    } catch (NotVerifiedException e) {
        String errorJson = "{\"error\":\"" + e.getMessage() + "\"}";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorJson);
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
        UserDto userDto = new UserDto(user.getName(), user.getEmail(),
                user.getTelephoneNumber(), user.getUsername(), user.getIsVerified(),
                user.getIsFullyVerified(), user.getVerifiedBy(),
                user.getRoles());
        userDtos.add(userDto);
    }
    return ResponseEntity.ok(userDtos);
}

    @GetMapping("/who")
    public ResponseEntity<User> getUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(user);
    }

    private UserDto mapUserToUserDto(User savedUser) {
        UserDto userDto = new UserDto();
        userDto.setUserId(savedUser.getUserId());
        userDto.setName(savedUser.getName());
        userDto.setEmail(savedUser.getEmail());
        userDto.setPassword(savedUser.getPassword());
        userDto.setTelephoneNumber(savedUser.getTelephoneNumber());
        userDto.setUsername(savedUser.getUsername());
        userDto.setIsVerified(savedUser.getIsVerified());
        userDto.setIsFullyVerified(savedUser.getIsFullyVerified());
        userDto.setVerifiedBy(savedUser.getVerifiedBy());
        userDto.setRoles(savedUser.getRoles());
        return userDto;
    }

}
