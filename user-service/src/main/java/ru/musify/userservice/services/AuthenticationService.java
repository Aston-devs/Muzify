package ru.musify.userservice.services;

import ru.musify.userservice.dto.SignUpRequest;
import ru.musify.userservice.model.User;

public interface AuthenticationService {
    User signup(SignUpRequest signUpRequest);
}
