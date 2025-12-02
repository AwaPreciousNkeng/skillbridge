package com.codewithpcodes.skillbridge.auth;

import com.codewithpcodes.skillbridge.user.Role;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        String profileImage,
        Role role
) {
}
