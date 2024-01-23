package ru.musify.userservice.dto;

/**
 * This record represents a response data containing a token, user ID, and role.
 */
public record ResponseData(String token, String userId, String role) {
}