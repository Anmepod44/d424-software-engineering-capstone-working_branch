package com.wgu.taskmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * Project entity demonstrating INHERITANCE and ENCAPSULATION OOP principles.
 * 
 * INHERITANCE: Extends BaseEntity to inherit common fields (id, createdAt, updatedAt)
 * ENCAPSULATION: Private fields with public getters/setters and business logic methods
 */
@Entity
@Table(name = "projects")
public class Project extends BaseEntity {
    
    /**
     * Project name - encapsulated with validation
     */
    @Column(nullable = false, length = 100)
    @NotBlank(message = "Project name is required")
    @Size(max = 100, message = "Project name must not exceed 100 characters")
    private String name;
    
    /**
     * Project description - encapsulated
     */
    @Column(columnDefinition = "TEXT")
    private String description;
    
    /**
     * Project owner - encapsulated relationship
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;
    
    /**
     * Tasks in this project - encapsulated relationship with cascade
     */
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();
    
    // Constructors
    
    public Project() {
    }
    
    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    // Encapsulated business logic methods
    
    /**
     * Gets total number of tasks in project
     * Demonstrates encapsulation by providing controlled access to derived data
     */
    public int getTaskCount() {
        return tasks.size();
    }
    
    /**
     * Gets number of completed tasks in project
     * Demonstrates encapsulation by providing business logic method
     */
    public int getCompletedTaskCount() {
        return (int) tasks.stream()
                .filter(t -> t.getStatus() == TaskStatus.COMPLETED)
                .count();
    }
    
    /**
     * Calculates project completion percentage
     * Demonstrates encapsulation by providing derived business data
     */
    public double getCompletionPercentage() {
        if (tasks.isEmpty()) {
            return 0.0;
        }
        return (double) getCompletedTaskCount() / tasks.size() * 100.0;
    }
    
    /**
     * Adds a task to the project
     * Demonstrates encapsulation by managing bidirectional relationship
     */
    public void addTask(Task task) {
        tasks.add(task);
        task.setProject(this);
    }
    
    /**
     * Removes a task from the project
     * Demonstrates encapsulation by managing bidirectional relationship
     */
    public void removeTask(Task task) {
        tasks.remove(task);
        task.setProject(null);
    }
    
    // Getters and Setters - Demonstrating Encapsulation
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public User getOwner() {
        return owner;
    }
    
    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    public List<Task> getTasks() {
        return tasks;
    }
    
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
