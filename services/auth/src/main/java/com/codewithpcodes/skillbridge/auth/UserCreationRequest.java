package com.codewithpcodes.skillbridge.auth;

import com.codewithpcodes.skillbridge.user.Role;

/**
 * DTO used by the Auth Service to send non-sensitive profile data
 * to the dedicated User Service for profile creation.
 */
public record UserCreationRequest(
        String email,
        String firstName,
        String lastName,
        Role role
) {
}
