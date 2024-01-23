package ru.musify.userservice.services.impl;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musify.userservice.dto.SignUpRequest;
import ru.musify.userservice.mapper.UserMapper;
import ru.musify.userservice.model.User;
import ru.musify.userservice.repository.UserRepository;
import ru.musify.userservice.services.AuthenticationService;

/**
 * This class provides the implementation for user authentication operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    /**
     * The mapper for converting SignUpRequest DTOs to User entities.
     */
    private final UserMapper mapper;

    /**
     * The repository for accessing user data.
     */
    private final UserRepository userRepository;

    /**
     * The encoder for encoding and verifying passwords.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Signs up a new user based on the provided sign-up request.
     *
     * @param request The sign-up request containing user details.
     * @return The user entity representing the signed-up user.
     */
    @Transactional(rollbackFor = ValidationException.class)
    public User signup(SignUpRequest request) {
        User user = mapper.toUser(request);
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(request.password()));
        return userRepository.save(user);
    }
}