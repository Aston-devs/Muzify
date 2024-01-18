package ru.musify.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.musify.userservice.dto.LoginRequest;
import ru.musify.userservice.dto.SignUpRequest;
import ru.musify.userservice.services.AuthenticationService;
import ru.musify.userservice.services.JwtService;
import ru.musify.userservice.services.impl.UserDetailsServiceImpl;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    private final JwtService jwtService;

    private final UserDetailsServiceImpl userDetailsService;

    private final AuthenticationService authenticationService;

    private final AuthenticationManager authenticationManager;

    private UUID getUserID(String userName) {
        return userDetailsService.loadUserByUsername(userName).getID();
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registration(@RequestBody SignUpRequest request) {
        authenticationService.signup(request);
        return ResponseEntity.ok(jwtService.generateToken(request.username(), getUserID(request.username())));
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
        return ResponseEntity.ok(jwtService.generateToken(request.username(), getUserID(request.username())));
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        jwtService.validateTokenAndRetrieveClaim(token);
        return "Token is valid";
    }
}
