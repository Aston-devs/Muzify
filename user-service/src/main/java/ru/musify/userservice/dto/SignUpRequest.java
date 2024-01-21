package ru.musify.userservice.dto;

import lombok.Builder;

@Builder
public record SignUpRequest(String email, String username, String password) {
}
