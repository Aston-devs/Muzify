package ru.musify.gateway.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.musify.gateway.util.JWTUtil;


/**
 * Authentication filter for handling authorization and user roles
 * with properties <b>jwtUtil</b>, <b>secret</b>, <b>ROLE_ADMIN</b>
 */
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    /**
     * Validator for checking routes
     */
    private final RouteValidator validator;

    /**
     * Utility for handling JSON Web Tokens
     */
    private final JWTUtil jwtUtil;

    /**
     * The role name for administrators
     */
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    /**
     * Constructor for AuthenticationFilter.
     * @param validator - the route validator
     * @param jwtUtil - the JWT utility
     */
    @Autowired
    public AuthenticationFilter(RouteValidator validator, JWTUtil jwtUtil) {
        super(Config.class);
        this.validator = validator;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Handles error response.
     * @param exchange - the server web exchange
     * @param httpStatus - the HTTP status
     * @return a Mono<Void> representing the completion of the response
     */
    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    /**
     * Applies the filter to the request.
     * @param config - the configuration
     * @return the GatewayFilter for the request
     */
    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            if (validator.isSecured.test(request)) {
                if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing authorization header");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                    try {
                        Jws<Claims> jws = jwtUtil.validateToken(authHeader);
                        String userID = jws.getBody().getSubject();
                        String userRole = jwtUtil.getUserRole(jws);
                        if (validator.isAdminRout.test(request)) {
                            if (!userRole.contains(ROLE_ADMIN)) {
                                throw new RuntimeException("un authorized access to application");
                            }
                        }
                        request.mutate().header("loggedInUser", userID).build();
                        response.getHeaders().add("userID", userID);
                    } catch (JWTVerificationException e) {
                        throw new RuntimeException("un authorized access to application");
                    }
                }
            }
            return chain.filter(exchange.mutate().request(request).response(response).build());
        }));
    }

    /**
     * Configuration class for AuthenticationFilter.
     */
    public static class Config {
    }

}