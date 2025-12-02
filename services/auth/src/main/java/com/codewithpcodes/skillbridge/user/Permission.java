package com.codewithpcodes.skillbridge.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Defines the atomic permissions that can be granted to a user role.
 * These strings are used in Spring's @PreAuthorize annotations (e.g., "admin:read").
 */
@RequiredArgsConstructor
public enum Permission {

    // ADMIN Permissions (for system management)
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    // MENTORSHIP Permissions
    MENTOR_READ_OWN_PROJECTS("mentor:read_own_projects"),
    MENTOR_CREATE_PROJECTS("mentor:create_projects"),
    MENTOR_REVIEW_MENTEE("mentor:review_mentee"),

    // MENTEE Permissions
    MENTEE_VIEW_PROJECTS("mentee:view_projects"),
    MENTEE_APPLY_PROJECT("mentee:apply_project"),

    // CLIENT Permissions
    CLIENT_VIEW_APPLICATIONS("client:view_applications"),
    CLIENT_POST_JOB("client:post_job")

    // ... many more specific permissions could be added here
    ;

    @Getter
    private final String permission;
}