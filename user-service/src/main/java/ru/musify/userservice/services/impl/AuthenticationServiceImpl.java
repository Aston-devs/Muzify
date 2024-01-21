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

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserMapper mapper;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = ValidationException.class)
    public User signup(SignUpRequest request) {
        User user = mapper.toUser(request);
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(request.password()));
        return userRepository.save(user);
    }
}
