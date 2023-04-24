package com.cs490.shoppingcart.filter;

import com.cs490.shoppingcart.UnauthorizedException.UnAuthorizedException;
import com.cs490.shoppingcart.util.JwtUtil;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

@Component
@RefreshScope
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteValidator validator;
    private final JwtUtil jwtUtil;

    public AuthenticationFilter(RouteValidator validator, JwtUtil jwtUtil) {
        super(Config.class);
        this.validator = validator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                try {
                    // Check if the request header contains an authorization token
                    if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                        throw new UnAuthorizedException("You are unauthorized to perform this action, please login");
                    }
                    // Extract the token from the authorization header
                    String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                    if (authHeader != null && authHeader.startsWith("Bearer ")) {
                        authHeader = authHeader.substring(7);
                    }
                    // Validate the JWT token
                    jwtUtil.validateToken(authHeader);
                } catch (UnAuthorizedException e) {
                    // If the request is unauthorized, set the response status to 401 and return an error message
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    String errorJson = "{\"error\":\"" + e.getMessage() + "\"}";
                    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(errorJson.getBytes(StandardCharsets.UTF_8));
                    return exchange.getResponse().writeWith(Flux.just(buffer));
                } catch (Exception e) {
                    // If an unexpected error occurs, set the response status to 500 and return an error message
                    exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                    exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//                    String errorJson = "{\"error\":\"An unexpected error occurred\"}";
                    String errorJson = "{\"error\":\"" + e.getMessage() + "\"}";
                    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(errorJson.getBytes(StandardCharsets.UTF_8));
                    return exchange.getResponse().writeWith(Flux.just(buffer));
                }
            }
            // If the request is authorized, continue processing the request
            return chain.filter(exchange);
        });
    }
    public static class Config {

    }
}

