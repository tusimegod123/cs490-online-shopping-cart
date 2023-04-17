package com.cs490.shoppingcart.administrationmodule.config;

import com.cs490.shoppingcart.administrationmodule.model.Role;
import com.cs490.shoppingcart.administrationmodule.model.User;
import com.cs490.shoppingcart.administrationmodule.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;

//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
@Component
public class JwtTokenFilter extends OncePerRequestFilter{
    private final JwtService jwtUtil;

//clear

    public  final  String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public JwtTokenFilter(JwtService jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!hasAuthorizationBearer(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = getAccessToken(request);
        if (!jwtUtil.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        setAuthenticationContext(token, request);
        filterChain.doFilter(request, response);
    }

    private boolean hasAuthorizationBearer(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
            return false;
        }
        return true;
    }

    private String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.split(" ")[1].trim();
        return token;
    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(token);
        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

//    private User getUserDetails(String jwtToken) {
//        User userDetails = new User();
//
//        String subject = Jwts.parserBuilder()
//                .setSigningKey(SECRET)
//                .build()
//                .parseClaimsJws(jwtToken)
//                .getBody()
//                .getSubject();
//
//        String[] jwtSubject = subject.split(",");
//        if (jwtSubject.length >= 1) {
//            userDetails.setEmail(jwtSubject[0]);
//        }
//        if (jwtSubject.length >= 2) {
//            userDetails.setName(jwtSubject[1]);
//        }
////        if (jwtSubject.length >= 3) {
////            userDetails.setUserId(Long.parseLong(jwtSubject[2]));
////        }
//        if (jwtSubject.length >= 3) {
//            userDetails.setTelephoneNumber(jwtSubject[3]);
//        }
//        if (jwtSubject.length >= 4) {
//            userDetails.setUsername(jwtSubject[4]);
//        }
//        if (jwtSubject.length >= 5) {
//            userDetails.setIsVerified(Boolean.parseBoolean(jwtSubject[5]));
//        }
//        if (jwtSubject.length >= 6) {
//            userDetails.setIsFullyVerified(Boolean.parseBoolean(jwtSubject[6]));
//        }
//        if (jwtSubject.length >= 7) {
//            userDetails.setRegistrationFee(Double.parseDouble(jwtSubject[7]));
//        }
//        if (jwtSubject.length >= 8) {
//            userDetails.setPaymentCardNumber(jwtSubject[8]);
//        }
//        if (jwtSubject.length >= 9) {
//            userDetails.setVerifiedBy(jwtSubject[9]);
//        }
//
//        return userDetails;
//    }


    private UserDetails getUserDetails(String token) {
        User userDetails = new User();
        Claims claims = jwtUtil.parseClaims(token);
        String subject = (String) claims.get(Claims.SUBJECT);
        String name = (String) claims.get("name");
        String roles = (String) claims.get("roles");
        System.out.println("SUBJECT: " + subject);
        System.out.println("roles: " + roles);
        roles = roles.replace("[", "").replace("]", "");
        String[] roleNames = roles.split(",");
        for (String aRoleName : roleNames) {
            userDetails.addRole(new Role(aRoleName));
        }
        String[] jwtSubject = subject.split(",");
      //  userDetails.setUserId(Long.parseLong(jwtSubject[0]));
        userDetails.setEmail(jwtSubject[0]);
        userDetails.setName(name);
//        userDetails.setName(jwtSubject[1]);
        return userDetails;
    }
}
