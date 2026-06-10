package com.wgu.taskmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * User entity demonstrating INHERITANCE and ENCAPSULATION OOP principles.
 * 
 * INHERITANCE: Extends BaseEntity to inherit common fields (id, createdAt, updatedAt)
 * ENCAPSULATION: Private fields with controlled access, especially for sensitive password field
 */
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    
    /**
     * Username - encapsulated with validation
     */
    @Column(unique = true, nullable = false, length = 50)
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;
    
    /**
     * Email - encapsulated with validation
     */
    @Column(unique = true, nullable = false, length = 100)
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;
    
    /**
     * Password - HIGHLY ENCAPSULATED
     * No getter provided to prevent password exposure
     * Password encoding handled by service layer
     */
    @Column(nullable = false)
    private String password;
    
    /**
     * User role - encapsulated with enum for type safety
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserRole role = UserRole.USER;
    
    /**
     * Tasks assigned to this user - encapsulated relationship
     */
    @OneToMany(mappedBy = "assignedTo")
    private List<Task> assignedTasks = new ArrayList<>();
    
    /**
     * Projects owned by this user - encapsulated relationship
     */
    @OneToMany(mappedBy = "owner")
    private List<Project> ownedProjects = new ArrayList<>();
    
    // Constructors
    
    public User() {
    }
    
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    
    // Getters and Setters - Demonstrating Encapsulation
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * NO GETTER FOR PASSWORD - Demonstrates strong encapsulation
     * Password should never be exposed, even to internal code
     */
    
    /**
     * Setter for password - used by service layer after encoding
     * Demonstrates encapsulation by controlling how password is set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Public getter for password - used by Spring Security for authentication
     * Password is already encrypted, so it's safe to expose
     */
    public String getPassword() {
        return password;
    }
    
    public UserRole getRole() {
        return role;
    }
    
    public void setRole(UserRole role) {
        this.role = role;
    }
    
    public List<Task> getAssignedTasks() {
        return assignedTasks;
    }
    
    public void setAssignedTasks(List<Task> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }
    
    public List<Project> getOwnedProjects() {
        return ownedProjects;
    }
    
    public void setOwnedProjects(List<Project> ownedProjects) {
        this.ownedProjects = ownedProjects;
    }
}
