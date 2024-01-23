package ru.musify.userservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.musify.userservice.repository.UserRepository;

/**
 * Provides the implementation for retrieving user details by username.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * The repository for accessing user data.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Loads user details by username.
     *
     * @param username The username of the user.
     * @return The user details.
     * @throws UsernameNotFoundException If the user is not found.
     */
    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsImpl(userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }
}