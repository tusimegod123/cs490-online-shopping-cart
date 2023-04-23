package com.cs490.shoppingcart.administrationmodule.service;

import com.cs490.shoppingcart.administrationmodule.exception.UserNotFoundException;
import com.cs490.shoppingcart.administrationmodule.model.User;
import com.cs490.shoppingcart.administrationmodule.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtService {
    private  final UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);

//    @Value("${SECRET}")
    public  final  String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public JwtService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean validateToken(final String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            LOGGER.error("JWT expired", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.error("JWT is invalid", ex);
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("JWT is not supported", ex);
        } catch (SignatureException ex) {
            LOGGER.error("Signature validation failed");
        }

        return false;
//        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
//        return false;
    }
    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
    public String generateToken(String username) {
        return getToken(username);
    }
    private String createToken(Map<String, Object> claims, String userEmail) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userEmail)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody();
    }

    public String refreshToken(String username) {
        return getToken(username);
    }

    private String getToken(String username) {
        try {
            User user = userRepository.findByUsername(username).
                    orElseThrow(() -> new UserNotFoundException("Sorry this user is not yet registered to login, Sign up first then try again"));
            Map<String, Object> claims = new HashMap<>();
//            claims.put("userId", user.getUserId());
            claims.put("name", user.getName());
            claims.put("telephoneNumber", user.getTelephoneNumber());
            claims.put("username", user.getUsername());
            claims.put("email", user.getEmail());
            claims.put("verified", user.getIsVerified());
            claims.put("fullyVerified", user.getIsFullyVerified());
            claims.put("verifiedBy", user.getVerifiedBy());
            claims.put("role", user.getRoles().toString());
            return createToken(claims, username);
        } catch (UserNotFoundException userNotFoundException) {
            return userNotFoundException.getMessage();
        }
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
