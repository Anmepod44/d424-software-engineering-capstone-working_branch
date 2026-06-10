package com.wgu.taskmanagement.exception;

/**
 * Exception thrown when unauthorized access is attempted.
 * Extends TaskManagementException to maintain exception hierarchy.
 */
public class UnauthorizedException extends TaskManagementException {
    
    public UnauthorizedException(String message) {
        super(message);
    }
}
