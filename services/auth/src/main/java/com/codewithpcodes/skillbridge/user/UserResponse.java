package com.codewithpcodes.skillbridge.user;

public record UserResponse(
        Integer id,
        String firstName,
        String lastName,
        String email,
        Role role
) {
}
