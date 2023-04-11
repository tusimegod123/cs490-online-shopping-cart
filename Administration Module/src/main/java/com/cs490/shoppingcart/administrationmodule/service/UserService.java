package com.cs490.shoppingcart.administrationmodule.service;

import com.cs490.shoppingcart.administrationmodule.dto.UserDto;
import com.cs490.shoppingcart.administrationmodule.model.Role;
import com.cs490.shoppingcart.administrationmodule.model.User;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired
    private JwtService jwtService;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return RandomStringUtils.random(length, characters);
    }
    public String generateRandomUsername(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return RandomStringUtils.random(length, characters);
    }
//    public User createUser(UserDto userDto) {
//        User user = new User();
//
//        Set<Role> roles = userDto.getRoles().stream().map(roleDto -> {
//            Role role = new Role();
//            role.setRoleName(roleDto.getRoleName());
//            return role;
//        }).collect(Collectors.toSet());
//        if (userDto.getRoles().stream().anyMatch(role -> role.getRoleName().equals("Vendor"))){
//            String randomUsername= generateRandomUsername(6);
//            user.setUsername(randomUsername); // set default username
//            String randomPassword = generateRandomPassword(8); // generate random password
//            user.setPassword(passwordEncoder.encode(randomPassword)); // encode password
//            userRepository.save(user); // save user to database
//            // send password to user via email or SMS
//            sendPasswordToUser(user, randomPassword);
//        }else {
//            user.setName(userDto.getName());
//            user.setEmail(userDto.getEmail());
//            user.setTelephoneNumber(userDto.getTelephoneNumber());
//            user.setRoles((List<Role>) roles);
//            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        }
//       return userRepository.save(user); // save user to database
//    }

    public User createUser(UserDto userDto) throws IllegalArgumentException {
//        if (userRepository.existsByEmail(userDto.getEmail())) {
//            throw new IllegalArgumentException("User with email " + userDto.getEmail() + " already exists.");
//        }
//        if (userRepository.existsByUsername(userDto.getUsername())) {
//            throw new IllegalArgumentException("User with username " + userDto.getUsername() + " already exists.");
//        }

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
            String randomUsername = generateRandomUsername(6);
            user.setUsername(randomUsername);
            String randomPassword = generateRandomPassword(8);
            user.setPassword(passwordEncoder.encode(randomUsername));
            sendPasswordToUser(user, randomPassword);
        } else {
            user.setUsername(userDto.getUsername());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }


    private void sendPasswordToUser(User user, String password) {
        // code to send password to user via email or SMS
        System.out.println("Hello " + user.getName() + "Your default user name is " + user.getUsername()  + "and default password is " + password);
    }


//    public User createUser(User user)  {
//        try{
//            if (userRepository.findByEmail(user.getEmail()) != null) {
//                 throw new EmailExistsException("Email already exists.");
//            }
//        }catch (EmailExistsException e){
//            e.getMessage();
//        }
//        return userRepository.save(user);
//    }

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
