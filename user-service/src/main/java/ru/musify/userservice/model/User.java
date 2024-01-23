package ru.musify.userservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * This class represents a user entity in the user-service.
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String role;
    private String email;
    private String username;
    private String password;
}