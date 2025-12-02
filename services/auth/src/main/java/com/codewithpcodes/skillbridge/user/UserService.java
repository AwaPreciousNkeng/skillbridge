package com.codewithpcodes.skillbridge.user;

import com.codewithpcodes.skillbridge.exception.IncorrectPasswordException;
import com.codewithpcodes.skillbridge.exception.PasswordMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * Service to handle user-related business logic, including secure password changes.
 * Uses custom exceptions for cleaner error handling.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    /**
     * Handles the secure change of a user's password.
     * 1. Retrieves the authenticated user.
     * 2. Verifies the current password against the stored hash.
     * 3. Verifies the new password matches the confirmation password.
     * 4. Encodes and saves the new password hash to the database.
     *
     * @param request The ChangePasswordRequest DTO containing old and new passwords.
     * @param connectedUser The currently authenticated user Principal.
     * @throws IncorrectPasswordException if the current password is wrong.
     * @throws PasswordMismatchException if the new passwords do not match.
     */
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        // Step 1: Extract the User entity from the Principal object
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // Safety check (should generally not happen if security is configured)
        if (user == null) {
            throw new IllegalStateException("Authenticated user object is null.");
        }

        // Step 2: Check if the current password is correct (Secure comparison)
        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            // Throw custom exception for security-sensitive failure
            throw new IncorrectPasswordException("Incorrect current password provided.");
        }

        // Step 3: Check if the two new passwords are the same (Input validation)
        if (!request.newPassword().equals(request.confirmationPassword())) {
            // Throw custom exception for validation failure
            throw new PasswordMismatchException("New password and confirmation password do not match.");
        }

        // Step 4: Update the password with the new hash
        user.setPassword(passwordEncoder.encode(request.newPassword()));

        // Step 5: Save the user with the new password hash
        userRepository.save(user);
    }
}