package ru.musify.userservice;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.musify.userservice.model.User;
import ru.musify.userservice.repository.UserRepository;

/**
 * Main application for the user-service.
 */
@SpringBootApplication
@RequiredArgsConstructor
public class UserServiceApplication implements CommandLineRunner {

    /**
     * The role for the admin user.
     */
    private static final String ROLE = "ROLE_ADMIN";

    /**
     * The repository for accessing user data.
     */
    private final UserRepository userRepository;

    /**
     * The entry point for the user service application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    /**
     * Runs the application and initializes the admin account if it doesn't exist.
     *
     * @param args The command-line arguments.
     * @throws Exception If an error occurs during execution.
     */
    @Override
    public void run(String... args) throws Exception {
        User adminAccount = userRepository.findByRole(ROLE).orElseGet(() -> {
            return User.builder()
                    .role("ROLE_ADMIN")
                    .email("admin@gmail.com")
                    .username("musify_admin")
                    .password(new BCryptPasswordEncoder().encode("admin"))
                    .build();
        });
        userRepository.save(adminAccount);
    }
}