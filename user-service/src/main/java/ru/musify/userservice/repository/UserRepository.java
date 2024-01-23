package ru.musify.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.musify.userservice.model.User;

import java.util.Optional;

/**
 * This interface provides access to the user repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by username.
     *
     * @param username The username of the user to be found.
     * @return An optional containing the user if found, empty otherwise.
     */
    Optional<User> findUserByUsername(String username);

    /**
     * Finds a user by role.
     *
     * @param role The role of the user to be found.
     * @return An optional containing the user if found, empty otherwise.
     */
    Optional<User> findByRole(String role);
}