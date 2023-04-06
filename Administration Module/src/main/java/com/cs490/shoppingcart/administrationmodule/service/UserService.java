package com.cs490.shoppingcart.administrationmodule.service;

import com.cs490.shoppingcart.administrationmodule.model.Role;
import com.cs490.shoppingcart.administrationmodule.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.cs490.shoppingcart.administrationmodule.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User updateUserDetails(User user, Long id){
        User userTobeUpdated = userRepository.findById(id).get();
        userTobeUpdated.setId(user.getId());
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

    public String generateToken(String username) {
//        String jwt = generateTokenFromUsername(userPrincipal.getUsername(), userPrincipal.getId(),userPrincipal.getRoles(),
//                userPrincipal.getTelephoneNumber(),userPrincipal.getEmail());
        return jwtService.generateToken(username);
    }
    public String generateTokenFromUsername(String username, Long userid, List<Role> roles, String name, String email) {
        return Jwts.builder()
                .setSubject(username).claim("userId", userid).claim("role", roles.toArray()).claim("name",name).claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
//                .signWith(SignatureAlgorithm.HS512, SECRET)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public void validateToken(String token) {
        jwtService.validateToken(token);
    }



}
