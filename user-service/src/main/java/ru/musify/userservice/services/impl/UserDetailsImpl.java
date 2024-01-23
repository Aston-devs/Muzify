package ru.musify.userservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.musify.userservice.model.User;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * This class represents the user details implementation for Spring Security.
 */
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    /**
     * The user entity representing the user details.
     */
    private final User user;

    /**
     * Retrieves the authorities granted to the user.
     *
     * @return The authorities granted to the user.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    /**
     * Retrieves the user's ID.
     *
     * @return The user's ID.
     */
    public UUID getID() {
        return user.getId();
    }

    /**
     * Retrieves the user's password.
     *
     * @return The user's password.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Retrieves the user's username.
     *
     * @return The user's username.
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Indicates whether the user's account has not expired.
     *
     * @return true if the user's account is valid, false otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is not locked.
     *
     * @return true if the user is not locked, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials have not expired.
     *
     * @return true if the user's credentials are valid, false otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled.
     *
     * @return true if the user is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}