package com.codewithpcodes.skillbridge.auth;

import org.springframework.stereotype.Component;

/**
 * Placeholder interface representing the HTTP client (e.g., WebClient)
 * used by the Auth Service to communicate with the separate User Service.
 * * In a real environment, this would be an HTTP client making a POST request
 * to the User Service endpoint (e.g., POST <a href="http://user-service/api/v1/profiles">...</a>).
 */
@Component
public class UserServiceClient {

    // Simulates an HTTP POST to the external User Service to create the profile
    public void createUserProfile(UserCreationRequest userCreationRequest) {
        // Log the action instead of making an HTTP call for simulation purposes
        System.out.println("AUTH SERVICE: Successfully sent profile creation request to User Service for: " + userCreationRequest.email());
        System.out.println("USER SERVICE: Created profile for role: " + userCreationRequest.role());
    }
}
