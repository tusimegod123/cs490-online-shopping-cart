package com.cs490.shoppingcart.administrationmodule.controller;


import com.cs490.shoppingcart.administrationmodule.dto.AuthRequest;
import com.cs490.shoppingcart.administrationmodule.dto.UserDto;
import com.cs490.shoppingcart.administrationmodule.exception.EmailExistsException;
import com.cs490.shoppingcart.administrationmodule.exception.InvalidCredentialsException;
import com.cs490.shoppingcart.administrationmodule.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.cs490.shoppingcart.administrationmodule.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final  PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }
    @PostMapping("/register")
    public User saveUser(@RequestBody UserDto user)  {
        return userService.createUser(user);
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
    @PutMapping(value = "/vendor/fullyVerify/{id}")
    public User fullyVerifyVendor(@RequestBody User vendor, @PathVariable Long id, @AuthenticationPrincipal User  admin) {
        return userService.fullyVerifyVendor(vendor, id,admin);
    }

    @GetMapping("/{id}")
    public User user(@PathVariable Long id){
        return userService.findUser(id);
    }
    @GetMapping
    public List<User> users(){
        return userService.allUsers();
    }

    @GetMapping("/who")
    public ResponseEntity<User> getUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(user);
    }

}
