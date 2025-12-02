package com.codewithpcodes.skillbridge.auth;

public record AuthenticationRequest(
        String email,
        String password
) {
}
