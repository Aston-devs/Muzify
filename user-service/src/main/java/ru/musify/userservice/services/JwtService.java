package ru.musify.userservice.services;

public interface JwtService {
    String generateToken(String username);
    String validateTokenAndRetrieveClaim(String token);
}
