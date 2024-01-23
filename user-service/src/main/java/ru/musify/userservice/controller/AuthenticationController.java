package ru.musify.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.musify.userservice.dto.LoginRequest;
import ru.musify.userservice.dto.ResponseData;
import ru.musify.userservice.dto.SignUpRequest;
import ru.musify.userservice.dto.UserMetainfo;
import ru.musify.userservice.services.AuthenticationService;
import ru.musify.userservice.services.JwtService;
import ru.musify.userservice.services.impl.KafkaProducerService;
import ru.musify.userservice.services.impl.UserDetailsServiceImpl;
import ru.musify.userservice.util.ErrorMessage;
import ru.musify.userservice.valid.UserValidator;

import java.util.Collection;
import java.util.UUID;

/**
 * This class represents the REST controller for handling authentication-related endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    /**
     * The service for JWT operations.
     */
    private final JwtService jwtService;

    /**
     * The validator for user input.
     */
    private final UserValidator userValidator;

    /**
     * The service for user details.
     */
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * The service for user authentication.
     */
    private final AuthenticationService authenticationService;

    /**
     * The service for producing Kafka messages.
     */
    private final KafkaProducerService kafkaProducerService;

    /**
     * The authentication manager.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Retrieves the user ID based on the username.
     *
     * @param userName The username of the user.
     * @return The ID of the user.
     */
    private UUID getUserID(String userName) {
        return userDetailsService.loadUserByUsername(userName).getID();
    }

    /**
     * Retrieves the roles associated with the user.
     *
     * @param username The username of the user.
     * @return The roles associated with the user.
     */
    private Collection<? extends GrantedAuthority> getUserRole(String username) {
        return userDetailsService.loadUserByUsername(username).getAuthorities();
    }

    /**
     * Handles the user signup process.
     *
     * @param request        The signup request containing user details.
     * @param bindingResult  The result of the validation.
     * @return               The response entity containing the token and user information.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequest request, BindingResult bindingResult) {
        userValidator.validate(request, bindingResult);
        if (bindingResult.hasErrors()) {
            String errorMessage = ErrorMessage.bindingResultHasErrors(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        authenticationService.signup(request);
        String username = request.username();
        String userId = String.valueOf(getUserID(username));
        Collection<? extends GrantedAuthority> role = getUserRole(username);
        String token = jwtService.generateToken(username, UUID.fromString(userId), role);
        kafkaProducerService.sendUserIdToTopic(new UserMetainfo(userId));
        return ResponseEntity.ok(new ResponseData(token, userId, role.toString()));
    }

    /**
     * Handles the user login process.
     *
     * @param request  The login request containing user credentials.
     * @return         The response entity containing the token and user information.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                request.username(), request.password()
        );
        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect credentials!");
        }
        String username = request.username();
        String userId = String.valueOf(getUserID(username));
        Collection<? extends GrantedAuthority> role = getUserRole(username);
        String token = jwtService.generateToken(username, UUID.fromString(userId), role);
        return ResponseEntity.ok(new ResponseData(token, userId, role.toString()));
    }

    /**
     * Validates the provided token.
     *
     * @param token  The token to be validated.
     * @return       The validation result.
     */
    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        jwtService.validateTokenAndRetrieveClaim(token);
        return "Token is valid";
    }
}