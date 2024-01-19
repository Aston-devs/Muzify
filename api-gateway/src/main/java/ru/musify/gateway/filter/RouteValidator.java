package ru.musify.gateway.filter;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public static final List<String> OPEN_API_ENDPOINTS = List.of(
            "/api/v1/auth/signup",
            "/api/v1/auth/login",
            "/api/v1/auth/validate",
            "/eureka"
    );

    public static final List<String> ADMIN_API_ENDPOINTS = List.of(
            "/api/v1/player/upload"
    );

    public Predicate<ServerHttpRequest> isAdminRout =
            request -> ADMIN_API_ENDPOINTS
                    .stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));
    public Predicate<ServerHttpRequest> isSecured =
            request -> OPEN_API_ENDPOINTS
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
