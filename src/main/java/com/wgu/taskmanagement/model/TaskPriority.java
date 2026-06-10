package com.wgu.taskmanagement.model;

/**
 * Enum representing the priority level of a task.
 * Demonstrates type safety and encapsulation of valid priority values.
 */
public enum TaskPriority {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    URGENT("Urgent");
    
    private final String displayName;
    
    TaskPriority(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
