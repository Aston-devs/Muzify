package ru.musify.userservice.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.musify.userservice.services.JwtService;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class JWTServiceImpl implements JwtService {

    @Value("${jwt_secret}")
    private String secret;

    private Key getKey() {
        return new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateToken(String username, UUID id) {
        Instant now = Instant.now();
        String jwtToken = Jwts.builder()
                .claim("name", username)
                .setSubject(String.valueOf(id))
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(Instant.ofEpochSecond(System.currentTimeMillis() + 1_000 * 60 * 24)))
                .signWith(getKey())
                .compact();
        return jwtToken;
    }

    public String validateTokenAndRetrieveClaim(String token) {

        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token);

        return jwt.getBody().getSubject();
    }


}
