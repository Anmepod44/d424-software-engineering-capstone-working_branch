package com.wgu.taskmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Task entity demonstrating INHERITANCE and ENCAPSULATION OOP principles.
 * 
 * INHERITANCE: Extends BaseEntity to inherit common fields (id, createdAt, updatedAt)
 * ENCAPSULATION: Private fields with public getters/setters and validation business logic
 */
@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {
    
    /**
     * Task title - encapsulated with validation
     */
    @Column(nullable = false, length = 200)
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;
    
    /**
     * Task description - encapsulated
     */
    @Column(columnDefinition = "TEXT")
    private String description;
    
    /**
     * Task status - encapsulated with enum for type safety
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TaskStatus status = TaskStatus.PENDING;
    
    /**
     * Task priority - encapsulated with enum for type safety
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private TaskPriority priority = TaskPriority.MEDIUM;
    
    /**
     * Due date - encapsulated
     */
    @Column(name = "due_date")
    private LocalDateTime dueDate;
    
    /**
     * Relationship to project - encapsulated with lazy loading
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
    
    /**
     * User assigned to task - encapsulated with lazy loading
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private User assignedTo;
    
    /**
     * User who created task - encapsulated with lazy loading
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    // Constructors
    
    public Task() {
    }
    
    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }
    
    // Encapsulated validation business logic
    
    /**
     * Validates if task data is valid
     * Demonstrates encapsulation by providing controlled validation logic
     */
    public boolean isValid() {
        return title != null && !title.trim().isEmpty() && title.length() <= 200;
    }
    
    /**
     * Checks if task is overdue
     * Demonstrates encapsulation by providing business logic method
     */
    public boolean isOverdue() {
        return dueDate != null && 
               LocalDateTime.now().isAfter(dueDate) && 
               status != TaskStatus.COMPLETED;
    }
    
    // Getters and Setters - Demonstrating Encapsulation
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public TaskStatus getStatus() {
        return status;
    }
    
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
    
    public TaskPriority getPriority() {
        return priority;
    }
    
    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }
    
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    
    public Project getProject() {
        return project;
    }
    
    public void setProject(Project project) {
        this.project = project;
    }
    
    public User getAssignedTo() {
        return assignedTo;
    }
    
    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }
    
    public User getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
