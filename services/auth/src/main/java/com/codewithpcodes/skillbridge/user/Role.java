package com.codewithpcodes.skillbridge.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.codewithpcodes.skillbridge.user.Permission.*;

@RequiredArgsConstructor
public enum Role {


    // 1. Mentee Role: Can view projects and apply to them.
    MENTEE(Set.of(
            MENTEE_VIEW_PROJECTS,
            MENTEE_APPLY_PROJECT
    )),

    // 2. Mentor Role: Can manage their own projects and review mentees.
    MENTOR(Set.of(
            MENTOR_READ_OWN_PROJECTS,
            MENTOR_CREATE_PROJECTS,
            MENTOR_REVIEW_MENTEE
    )),

    // 3. Client Role: Can view applications and post new job/project opportunities.
    CLIENT(Set.of(
            CLIENT_VIEW_APPLICATIONS,
            CLIENT_POST_JOB
    )),

    // 4. Admin Role: Has full system access (CRUD operations).
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    MENTOR_CREATE_PROJECTS, // Admins might need to act as super-mentors
                    CLIENT_POST_JOB         // Admins might need to post jobs
            )
    );

    @Getter
    private final Set<Permission> permission;

    /**
     * Converts the set of custom Permissions into a List of SimpleGrantedAuthority objects
     * required by Spring Security.
     * * This method adds two types of authorities:
     * 1. Specific permissions (e.g., "admin:read") for fine-grained @PreAuthorize checks.
     * 2. The canonical role name (e.g., "ROLE_ADMIN") for "hasRole()" checks.
     * * @return A List of SimpleGrantedAuthority objects representing the role and its permissions.
     */
    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermission()
                .stream()
                // Map each custom Permission enum value to a Spring Security authority string
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        // Add the base role authority, prefixed with "ROLE_", which is standard
        // for Spring's hasRole() checks.
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
