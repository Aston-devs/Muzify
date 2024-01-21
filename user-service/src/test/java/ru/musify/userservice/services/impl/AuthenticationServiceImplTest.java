package ru.musify.userservice.services.impl;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.musify.userservice.model.User;
import ru.musify.userservice.dto.SignUpRequest;
import ru.musify.userservice.mapper.UserMapper;
import ru.musify.userservice.repository.UserRepository;

@ContextConfiguration(classes = {AuthenticationServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AuthenticationServiceImplTest {

    @MockBean
    private User expUser;

    @Autowired
    private AuthenticationServiceImpl authenticationServiceImpl;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {

        expUser = User.builder()
                .email("user@example")
                .id(UUID.randomUUID())
                .password("password")
                .role("role_user")
                .username("user_name")
                .build();

    }

    @Test
    @DisplayName("Test Signup - successful registration")
    void testSignup() {

        when(passwordEncoder.encode(Mockito.any())).thenReturn("secret");
        when(userMapper.toUser(Mockito.any())).thenReturn(expUser);
        when(userRepository.save(Mockito.any())).thenReturn(expUser);

        User actualSignupResult = authenticationServiceImpl.signup(
                new SignUpRequest(
                        "actual@example",
                        "user_name",
                        "password"
                )
        );

        verify(userRepository).save(Mockito.any());
        verify(passwordEncoder).encode(Mockito.any());
        verify(userMapper).toUser(Mockito.any());
        assertSame(expUser, actualSignupResult);

    }
}


