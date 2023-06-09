package com.cs490.shoppingcart.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    //Routes that can be accessed by anyone, whether logged in or not
    public static final List<String> openApiEndpoints = List.of(
            "/api/v1/users/register",
            "/api/v1/users/login",
            "/api/v1/products/verified",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
