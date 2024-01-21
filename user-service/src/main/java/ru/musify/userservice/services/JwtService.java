package ru.musify.userservice.services;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

public interface JwtService {
    String generateToken(String username, UUID id, Collection<? extends GrantedAuthority> roles);

    String validateTokenAndRetrieveClaim(String token);

}
