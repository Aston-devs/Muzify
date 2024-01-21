package ru.musify.userservice.services.impl;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.musify.userservice.model.User;
import ru.musify.userservice.repository.UserRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ContextConfiguration(classes = {UserDetailsServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserDetailsServiceImplTest {

    @MockBean
    private User expUser;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setup() {

        expUser = User.builder()
                .email("user@example")
                .id(UUID.randomUUID())
                .password("password")
                .role("role_user")
                .username("user_name")
                .build();
    }

    @Test
    @DisplayName("Test loadUserByUsername - user found")
    void testLoadUserByUsername() throws UsernameNotFoundException {

        Optional<User> ofResult = Optional.of(expUser);
        when(userRepository.findUserByUsername(Mockito.any())).thenReturn(ofResult);

        UserDetails actual = userDetailsServiceImpl.loadUserByUsername("user_name");

        verify(userRepository).findUserByUsername(Mockito.any());
        assertEquals("password", actual.getPassword());
    }

    @Test
    @DisplayName("Test loadUserByUsername - user not found")
    void testLoadUserByUsername2() throws UsernameNotFoundException {

        Optional<User> emptyResult = Optional.empty();

        when(userRepository.findUserByUsername(Mockito.any())).thenReturn(emptyResult);
        assertThrows(UsernameNotFoundException.class, () -> userDetailsServiceImpl.loadUserByUsername("user_name"));
        verify(userRepository).findUserByUsername(Mockito.any());
    }


    @Test
    @DisplayName("Test loadUserByUsername - exception")
    void testLoadUserByUsername3() throws UsernameNotFoundException {

        when(userRepository.findUserByUsername(Mockito.any()))
                .thenThrow(new UsernameNotFoundException("User not found"));
        assertThrows(UsernameNotFoundException.class, () -> userDetailsServiceImpl.loadUserByUsername("user_name"));
        verify(userRepository).findUserByUsername(Mockito.any());
    }
}
