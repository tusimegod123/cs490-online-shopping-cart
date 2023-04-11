package com.cs490.shoppingcart.administrationmodule.service;

import com.cs490.shoppingcart.administrationmodule.model.Role;
import com.cs490.shoppingcart.administrationmodule.model.User;
import com.cs490.shoppingcart.administrationmodule.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtService {
    private  final UserRepository userRepository;
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public JwtService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    public String generateToken(String userEmail) {
        User user = userRepository.findByEmail(userEmail).get();
        Set<String> roles = user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet());
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getName());
        claims.put("roles", roles);
        return createToken(claims, userEmail);
    }

private String createToken(Map<String, Object> claims, String userEmail) {

    return Jwts.builder()
            .setClaims(claims)
            .setSubject(userEmail)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
            .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
}

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
