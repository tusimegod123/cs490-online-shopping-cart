package com.cs490.shoppingcart.administrationmodule.controller;


import com.cs490.shoppingcart.administrationmodule.dto.AuthRequest;
import com.cs490.shoppingcart.administrationmodule.dto.UserDto;
import com.cs490.shoppingcart.administrationmodule.exception.EmailExistsException;
import com.cs490.shoppingcart.administrationmodule.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    public User saveUser(@RequestBody UserDto user) throws EmailExistsException {
      //  user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.createUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
//        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
//        User userDetails = (User) authenticate.getPrincipal();
//        String jwtCookie = userService.generateToken(userDetails);
//        if (authenticate.isAuthenticated()) {
//           return userService.generateToken(userDetails);
////            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,jwtCookie).body(new
////                    UserInfoResponse(userDetails.getId(),userDetails.getName(), userDetails.getEmail(),
////                    userDetails.getTelephoneNumber(),userDetails.getRoles()));
//        } else {
//            throw new RuntimeException("invalid access");
//        }

            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authenticate.isAuthenticated()) {
                return userService.generateToken(authRequest.getUsername());
            } else {
                throw new RuntimeException("invalid access");
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
    @GetMapping("/{id}")
    public User user(@PathVariable Long id){
        return userService.findUser(id);
    }
    @GetMapping
    public List<User> users(){
        return userService.allUsers();
    }

}
