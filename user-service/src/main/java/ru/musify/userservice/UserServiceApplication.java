package ru.musify.userservice;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.musify.userservice.model.User;
import ru.musify.userservice.repository.UserRepository;

@SpringBootApplication
@RequiredArgsConstructor
public class UserServiceApplication implements CommandLineRunner {

    private static final String ROLE = "ROLE_ADMIN";

    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

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
