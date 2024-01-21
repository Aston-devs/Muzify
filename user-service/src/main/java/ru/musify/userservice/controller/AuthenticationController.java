package ru.musify.userservice.controller;

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
import ru.musify.userservice.valid.UserValidator;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final JwtService jwtService;

    private final UserValidator userValidator;

    private final UserDetailsServiceImpl userDetailsService;

    private final AuthenticationService authenticationService;

    private final KafkaProducerService kafkaProducerService;

    private final AuthenticationManager authenticationManager;

    private UUID getUserID(String userName) {
        return userDetailsService.loadUserByUsername(userName).getID();
    }

    private Collection<? extends GrantedAuthority> getUserRole(String username) {
        return userDetailsService.loadUserByUsername(username).getAuthorities();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request, BindingResult bindingResult) {
        userValidator.validate(request, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this username is already registered");
        }
        authenticationService.signup(request);
        String username = request.username();
        String userId = String.valueOf(getUserID(username));
        String token = jwtService.generateToken(username, UUID.fromString(userId), getUserRole(username));
        kafkaProducerService.sendUserIdToTopic(new UserMetainfo(userId));
        return ResponseEntity.ok(new ResponseData(token, userId));
    }

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
        String token = jwtService.generateToken(username, UUID.fromString(userId), getUserRole(username));
        return ResponseEntity.ok(new ResponseData(token, userId));
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        jwtService.validateTokenAndRetrieveClaim(token);
        return "Token is valid";
    }
}
