package ru.musify.userservice.services;

import ru.musify.userservice.dto.SignUpRequest;
import ru.musify.userservice.model.User;

/**
 * This interface provides authentication-related operations such as user signup.
 */
public interface AuthenticationService {

    /**
     * Signs up a new user based on the provided sign-up request.
     *
     * @param signUpRequest The sign-up request containing user details.
     * @return The user entity representing the signed-up user.
     */
    User signup(SignUpRequest signUpRequest);
}