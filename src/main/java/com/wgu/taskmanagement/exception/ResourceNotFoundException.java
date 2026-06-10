package com.wgu.taskmanagement.exception;

/**
 * Exception thrown when a requested resource is not found.
 * Extends TaskManagementException to maintain exception hierarchy.
 */
public class ResourceNotFoundException extends TaskManagementException {
    
    public ResourceNotFoundException(String resource, Long id) {
        super(String.format("%s with id %d not found", resource, id));
    }
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
