package com.wgu.taskmanagement.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Base entity class demonstrating INHERITANCE and ENCAPSULATION OOP principles.
 * 
 * INHERITANCE: This abstract class is extended by Task, User, and Project entities,
 * providing common fields (id, createdAt, updatedAt) to all subclasses.
 * 
 * ENCAPSULATION: Fields are private with controlled access through public getters
 * and protected setters, protecting internal state while allowing subclass access.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    
    /**
     * Primary key - encapsulated with protected setter to prevent external modification
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Creation timestamp - automatically set on entity creation
     * Encapsulated: no setter provided, value managed by JPA auditing
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;
    
    /**
     * Last modification timestamp - automatically updated on entity modification
     * Encapsulated: no setter provided, value managed by JPA auditing
     */
    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    /**
     * Public getter for id - allows read access
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Protected setter for id - allows subclasses to set id but prevents external modification
     * Demonstrates encapsulation by controlling access
     */
    protected void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Public getter for createdAt - allows read access
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    /**
     * Public getter for updatedAt - allows read access
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    /**
     * Utility method to check if entity is persisted
     * Demonstrates encapsulation by providing controlled access to internal state
     */
    public boolean isPersisted() {
        return id != null;
    }
}
