package com.codewithpcodes.skillbridge.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * REST controller for handling user registration, authentication (login), and JWT token refresh.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    /**
     * Endpoint for user registration. Creates a new user and issues an initial JWT.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    /**
     * Endpoint for user login (authentication). Verifies credentials and issues tokens.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    /**
     * Endpoint for refreshing the JWT access token using a valid refresh token.
     * This method assumes the AuthenticationService will handle writing the new token
     * to the response, or that it is designed to manipulate the response directly
     * (e.g., setting an HTTP-only cookie).
     */
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        // NOTE: If service.refreshToken() could return a value, it would be cleaner,
        // but this signature is common when dealing with HttpServletResponse for cookies/headers.
        service.refreshToken(request, response);
    }
}