package com.wgu.taskmanagement.exception;

/**
 * Base exception class for all application-specific exceptions.
 * Demonstrates exception hierarchy and encapsulation of error handling.
 */
public class TaskManagementException extends RuntimeException {
    
    public TaskManagementException(String message) {
        super(message);
    }
    
    public TaskManagementException(String message, Throwable cause) {
        super(message, cause);
    }
}
