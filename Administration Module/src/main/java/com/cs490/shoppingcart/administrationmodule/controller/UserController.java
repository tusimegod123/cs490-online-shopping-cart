package com.cs490.shoppingcart.administrationmodule.controller;


import com.cs490.shoppingcart.administrationmodule.dto.AuthRequest;
import com.cs490.shoppingcart.administrationmodule.dto.UserDto;
import com.cs490.shoppingcart.administrationmodule.exception.InvalidCredentialsException;
import com.cs490.shoppingcart.administrationmodule.exception.NotVerifiedException;
import com.cs490.shoppingcart.administrationmodule.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
//    @PostMapping("/register")
//    public User saveUser(@RequestBody UserDto user)  {
//        return userService.createUser(user);
//    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
            User user = new User(userDto.getName(), userDto.getEmail(), userDto.getPassword(),
                    userDto.getTelephoneNumber(), userDto.getUsername(), userDto.getRoles());
            User savedUser = userService.createUser(user);
            UserDto savedUserDto = mapUserToUserDto(savedUser);
            return ResponseEntity.created(URI.create("/users/" + savedUserDto.getUserId()))
                    .body(savedUserDto);
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
    public User updateUser(@RequestBody User user, @PathVariable Long id){
        return userService.updateUserDetails(user,id);
    }

@PutMapping(value = "/vendor/verify/{id}")
public User verifyVendor(@RequestBody User vendor, @PathVariable Long id, @AuthenticationPrincipal User  admin) {
    return userService.verifyVendor(vendor, id,admin);
}
//    @PutMapping(value = "/vendor/fullyVerify/{id}")
//    public User fullyVerifyVendor(@RequestBody User vendor, @PathVariable Long id, @AuthenticationPrincipal User  admin) {
//        try {
//            return userService.fullyVerifyVendor(vendor, id,admin);
//        } catch (NotVerifiedException e) {
//            String errorJson = "{\"error\":\"" + e.getMessage() + "\"}";
//        }
//        return vendor;
//    }
@PutMapping(value = "/vendor/fullyVerify/{id}")
public ResponseEntity<?> fullyVerifyVendor(@RequestBody User vendor, @PathVariable Long id, @AuthenticationPrincipal User admin) {
    try {
        User verifiedVendor = userService.fullyVerifyVendor(vendor, id,admin);
        return ResponseEntity.ok().body(verifiedVendor);
    } catch (NotVerifiedException e) {
        String errorJson = "{\"error\":\"" + e.getMessage() + "\"}";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorJson);
    }
}


    @GetMapping("/{id}")
    public User user(@PathVariable Long id){
        return userService.findUser(id);
    }
//    @GetMapping
//    public List<User> users(){
//        return userService.allUsers();
//    }
@GetMapping
public ResponseEntity<List<UserDto>>getUsers() {
//        return userService.allUsers().stream().map(user -> modelMapper.map(user, UserDto.class))
//                .collect(Collectors.toList());
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

//    private UserDto mapUserDtoToUser(User user) {
//        UserDto userDto = new UserDto();
//        userDto.setUserId(user.getUserId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setTelephoneNumber(user.getTelephoneNumber());
//        userDto.setUsername(user.getUsername());
//        userDto.setIsVerified(user.getIsVerified());
//        userDto.setIsFullyVerified(user.getIsFullyVerified());
//        userDto.setVerifiedBy(user.getVerifiedBy());
//        userDto.setRoles(user.getRoles());
//        return userDto;
//    }

}
