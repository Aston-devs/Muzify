package ru.musify.userservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.musify.userservice.dto.SignUpRequest;
import ru.musify.userservice.mapper.UserMapper;
import ru.musify.userservice.model.User;
import ru.musify.userservice.repository.UserRepository;
import ru.musify.userservice.services.AuthenticationService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserMapper mapper;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signup(SignUpRequest request) {
        User user = mapper.toUser(request);
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(request.password()));
        return userRepository.save(user);
    }
}
