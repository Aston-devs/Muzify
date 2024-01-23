package ru.musify.userservice.services;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

/**
 * This interface provides JWT-related operations such as token generation and validation.
 */
public interface JwtService {

    /**
     * Generates a JWT token for the specified user.
     *
     * @param username The username of the user.
     * @param id       The ID of the user.
     * @param roles    The roles associated with the user.
     * @return The generated JWT token.
     */
    String generateToken(String username, UUID id, Collection<? extends GrantedAuthority> roles);

    /**
     * Validates the provided JWT token and retrieves the claim.
     *
     * @param token The JWT token to be validated.
     * @return The claim retrieved from the token.
     */
    String validateTokenAndRetrieveClaim(String token);
}