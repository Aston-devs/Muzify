package ru.musify.userservice.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ru.musify.userservice.services.JwtService;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * This class provides the implementation for JWT-related operations.
 */
@Service
public class JWTServiceImpl implements JwtService {

    /**
     * The secret key used for signing the JWT.
     */
    @Value("${jwt_secret}")
    private String secret;

    /**
     * Retrieves the secret key for signing the JWT.
     *
     * @return The secret key for signing the JWT.
     */
    private Key getKey() {
        return new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());
    }

    /**
     * Generates a JWT token for the specified user.
     *
     * @param username The username of the user.
     * @param id       The ID of the user.
     * @param roles    The roles associated with the user.
     * @return The generated JWT token.
     */
    public String generateToken(String username, UUID id, Collection<? extends GrantedAuthority> roles) {
        Instant now = Instant.now();
        String jwtToken = Jwts.builder()
                .claim("name", username)
                .claim("role", roles)
                .setSubject(String.valueOf(id))
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(Instant.ofEpochSecond(System.currentTimeMillis() + 1_000 * 60 * 24)))
                .signWith(getKey())
                .compact();
        return jwtToken;
    }

    /**
     * Validates the provided JWT token and retrieves the claim.
     *
     * @param token The JWT token to be validated.
     * @return The claim retrieved from the token.
     */
    public String validateTokenAndRetrieveClaim(String token) {

        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token);

        return jwt.getBody().getSubject();
    }
}