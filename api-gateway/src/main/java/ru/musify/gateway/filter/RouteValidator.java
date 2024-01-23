package ru.musify.gateway.filter;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

/**
 * Route validator for checking API endpoints.
 */
@Component
public class RouteValidator {

    /**
     * List of open API endpoints.
     */
    public static final List<String> OPEN_API_ENDPOINTS = List.of(
            "/api/v1/auth/signup",
            "/api/v1/auth/login",
            "/api/v1/auth/validate",
            "/eureka"
    );

    /**
     * List of admin API endpoints.
     */
    public static final List<String> ADMIN_API_ENDPOINTS = List.of(
            "/api/v1/admin/upload"
    );

    /**
     * Predicate to check if the request is for an admin route.
     */
    public Predicate<ServerHttpRequest> isAdminRout =
            request -> ADMIN_API_ENDPOINTS
                    .stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));
    public Predicate<ServerHttpRequest> isSecured =
            request -> OPEN_API_ENDPOINTS
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}