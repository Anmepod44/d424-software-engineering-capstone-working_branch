package com.wgu.taskmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.Locale;

/**
 * Base controller class demonstrating INHERITANCE OOP principle.
 * 
 * INHERITANCE: This abstract class is extended by TaskController, ProjectController,
 * and ReportController, providing common functionality to all subclasses.
 * 
 * ENCAPSULATION: Protected methods allow subclasses to access common functionality
 * while hiding implementation details from external code.
 */
@Controller
public abstract class BaseController {
    
    @Autowired
    protected MessageSource messageSource;
    
    /**
     * Handle errors with common error page
     * Demonstrates encapsulation by providing reusable error handling
     * 
     * @param e The exception
     * @param model The model
     * @return Error view name
     */
    protected String handleError(Exception e, Model model) {
        model.addAttribute("error", e.getMessage());
        model.addAttribute("errorType", "Error");
        return "error/500";
    }
    
    /**
     * Validate input data
     * Demonstrates encapsulation by providing common validation logic
     * 
     * @param data The data to validate
     * @return true if valid, false otherwise
     */
    protected boolean validateInput(Object data) {
        return data != null;
    }
    
    /**
     * Get localized message
     * Demonstrates encapsulation by providing common message retrieval
     * 
     * @param code Message code
     * @param args Message arguments
     * @return Localized message
     */
    protected String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, Locale.getDefault());
    }
    
    /**
     * Add success message to model
     * Demonstrates encapsulation by providing common success message handling
     * 
     * @param model The model
     * @param message The success message
     */
    protected void addSuccessMessage(Model model, String message) {
        model.addAttribute("successMessage", message);
    }
    
    /**
     * Add error message to model
     * Demonstrates encapsulation by providing common error message handling
     * 
     * @param model The model
     * @param message The error message
     */
    protected void addErrorMessage(Model model, String message) {
        model.addAttribute("errorMessage", message);
    }
}
