package com.wgu.taskmanagement.model;

/**
 * Enum representing user roles in the system.
 * Demonstrates type safety and encapsulation of valid role values.
 */
public enum UserRole {
    USER("User"),
    ADMIN("Administrator");
    
    private final String displayName;
    
    UserRole(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
