package com.wgu.taskmanagement.service;

import com.wgu.taskmanagement.exception.ResourceNotFoundException;
import com.wgu.taskmanagement.exception.ValidationException;
import com.wgu.taskmanagement.model.User;
import com.wgu.taskmanagement.model.UserRole;
import com.wgu.taskmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User service implementing CrudService interface - demonstrates POLYMORPHISM.
 * 
 * POLYMORPHISM: This class implements the CrudService<User, Long> interface,
 * providing User-specific implementations of the generic CRUD operations.
 * The same interface is implemented differently by TaskService and ProjectService.
 */
@Service
@Transactional
public class UserService implements CrudService<User, Long> {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    /**
     * Create a new user with password encryption
     * Polymorphic implementation of CrudService.create()
     * Demonstrates security requirement: passwords are encrypted before storage
     */
    @Override
    public User create(User user) {
        // Validate user before creation
        validateUser(user);
        
        // Encrypt password before saving - SECURITY REQUIREMENT
        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
        
        // Set default role if not provided
        if (user.getRole() == null) {
            user.setRole(UserRole.USER);
        }
        
        return userRepository.save(user);
    }
    
    /**
     * Find user by ID
     * Polymorphic implementation of CrudService.findById()
     */
    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }
    
    /**
     * Find all users
     * Polymorphic implementation of CrudService.findAll()
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    /**
     * Update existing user
     * Polymorphic implementation of CrudService.update()
     */
    @Override
    public User update(Long id, User user) {
        User existing = findById(id);
        
        // Update fields (excluding password - handled separately)
        existing.setUsername(user.getUsername());
        existing.setEmail(user.getEmail());
        existing.setRole(user.getRole());
        
        return userRepository.save(existing);
    }
    
    /**
     * Delete user by ID
     * Polymorphic implementation of CrudService.delete()
     */
    @Override
    public void delete(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }
    
    // Additional business methods specific to UserService
    
    /**
     * Find user by username
     * Used for authentication
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with username: " + username));
    }
    
    /**
     * Find user by email
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email: " + email));
    }
    
    /**
     * Check if username exists
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    /**
     * Check if email exists
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    /**
     * Update user password
     * Ensures password is encrypted
     */
    public void updatePassword(Long id, String newPassword) {
        User user = findById(id);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    
    /**
     * Validate user data
     * Demonstrates encapsulation of validation logic
     */
    private void validateUser(User user) {
        Map<String, String> errors = new HashMap<>();
        
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            errors.put("username", "Username is required");
        } else if (user.getUsername().length() < 3 || user.getUsername().length() > 50) {
            errors.put("username", "Username must be between 3 and 50 characters");
        } else if (userRepository.existsByUsername(user.getUsername())) {
            errors.put("username", "Username already exists");
        }
        
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            errors.put("email", "Email is required");
        } else if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.put("email", "Email must be valid");
        } else if (userRepository.existsByEmail(user.getEmail())) {
            errors.put("email", "Email already exists");
        }
        
        if (!errors.isEmpty()) {
            throw new ValidationException("User validation failed", errors);
        }
    }
}
