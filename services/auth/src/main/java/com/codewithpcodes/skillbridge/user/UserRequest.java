package com.codewithpcodes.skillbridge.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
        @NotBlank(message = "First name is mandatory")
        String firstName,
        @NotBlank(message = "Last name is mandatory")
        String lastName,
        @Email(message = "Email must be valid")
        @NotBlank(message = "Email is mandatory")
        String email,

        //Password is required for creation, optional for updated (if not changing password)
        String password,
        String profileImage,

        @NotNull(message = "Role is mandatory")
        Role role
) {
}
