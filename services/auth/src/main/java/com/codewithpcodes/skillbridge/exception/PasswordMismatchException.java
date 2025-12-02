package com.codewithpcodes.skillbridge.exception;

/**
 * Custom exception thrown when the new password and confirmation password do not match.
 * Allows controllers to map this to an appropriate HTTP status (e.g., 400 Bad Request).
 */
public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException(String message) {
        super(message);
    }
}
