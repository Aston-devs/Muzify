package ru.musify.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * This record represents a sign-up request containing an email, username, and password.
 */
public record SignUpRequest(@Email(message = "Apparently the email address is incorrect") String email,
                            @Size(min = 4, message = "This field must contain at least 4 characters") String username,
                            @Size(min = 5, message = " Password must contain at least 5 characters") String password) {
}