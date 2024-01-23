package ru.musify.userservice.dto;

/**
 * This record represents a login request containing a username and password.
 */
public record LoginRequest(String username, String password) {
}