package ru.musify.userservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.musify.userservice.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsImpl(userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }
}
