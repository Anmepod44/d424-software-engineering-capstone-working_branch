package com.wgu.taskmanagement.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application.
 * Catches exceptions and returns appropriate error views.
 * Demonstrates centralized error handling and encapsulation.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Handle ResourceNotFoundException
     * Returns 404 error page
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFound(ResourceNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("errorType", "Resource Not Found");
        return "error/404";
    }
    
    /**
     * Handle ValidationException
     * Returns validation error page with field-specific errors
     */
    @ExceptionHandler(ValidationException.class)
    public String handleValidation(ValidationException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("errors", ex.getErrors());
        model.addAttribute("errorType", "Validation Error");
        return "error/validation";
    }
    
    /**
     * Handle UnauthorizedException
     * Returns 403 forbidden page
     */
    @ExceptionHandler(UnauthorizedException.class)
    public String handleUnauthorized(UnauthorizedException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("errorType", "Unauthorized Access");
        return "error/403";
    }
    
    /**
     * Handle general exceptions
     * Returns 500 error page
     */
    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, Model model) {
        model.addAttribute("error", "An unexpected error occurred");
        model.addAttribute("errorType", "Internal Server Error");
        model.addAttribute("details", ex.getMessage());
        return "error/500";
    }
}
