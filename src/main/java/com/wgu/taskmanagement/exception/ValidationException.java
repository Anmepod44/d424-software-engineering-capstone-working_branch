package com.wgu.taskmanagement.exception;

import java.util.Map;

/**
 * Exception thrown when validation fails.
 * Contains a map of field-specific error messages.
 */
public class ValidationException extends TaskManagementException {
    
    private final Map<String, String> errors;
    
    public ValidationException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }
    
    public Map<String, String> getErrors() {
        return errors;
    }
}
