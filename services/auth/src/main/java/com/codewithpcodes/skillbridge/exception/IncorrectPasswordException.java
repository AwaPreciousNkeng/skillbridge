package com.codewithpcodes.skillbridge.exception;

/**
 * Custom exception thrown when the user provides an incorrect current password.
 * Allows controllers to map this to an appropriate HTTP status (e.g., 403 Forbidden).
 */
public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
