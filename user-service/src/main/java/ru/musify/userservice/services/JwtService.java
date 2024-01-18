package ru.musify.userservice.services;

import java.util.UUID;

public interface JwtService {
    String generateToken(String username, UUID id);
    String validateTokenAndRetrieveClaim(String token);

}
