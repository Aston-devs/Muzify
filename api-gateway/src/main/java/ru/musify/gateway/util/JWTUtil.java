package ru.musify.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;

/**
 * Utility class for handling JSON Web Tokens (JWT) with property <b>secret</b>
 */
@Component
public class JWTUtil {

    /**
     * Secret key for signing and validating tokens.
     */
    private final String secret = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    /**
     * Retrieves the key for token validation.
     * @return the key for token validation
     */
    private Key getKey() {
        return new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());
    }

    /**
     * Validates the JWT token.
     * @param token - the JWT token to validate
     * @return the parsed JWT claims
     */
    public Jws<Claims> validateToken(String token) {
        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token);

        return jwt;
    }

    /**
     * Retrieves the user role from the JWT claims.
     * @param jws - the parsed JWT claims
     * @return the user role as a string
     */
    public String getUserRole(Jws<Claims> jws) {
        ArrayList<String> roles = new ArrayList<>();
        Claims claims = jws.getBody();
        if (claims.containsKey("role")) {
            Object rolesObj = claims.get("role");
            if (rolesObj instanceof Collection rolesColl) {
                for (Object role : rolesColl) {
                    roles.add(role.toString());
                }
            }
        }
        return roles.toString();
    }
}