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

@Component
public class JWTUtil {

    private final String secret = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    private Key getKey() {
        return new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());
    }

    public Jws<Claims> validateToken(String token) {
        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token);

        return jwt;
    }

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