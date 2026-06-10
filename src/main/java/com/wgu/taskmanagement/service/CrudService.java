package com.wgu.taskmanagement.service;

import java.util.List;

/**
 * Generic CRUD service interface demonstrating POLYMORPHISM OOP principle.
 * 
 * POLYMORPHISM: This interface defines a contract that can be implemented by multiple
 * service classes (TaskService, UserService, ProjectService). Each implementation
 * provides its own specific behavior while adhering to the same interface contract.
 * 
 * This allows for:
 * - Code reusability through common interface
 * - Flexibility to swap implementations
 * - Consistent API across different entity services
 * 
 * @param <T> The entity type
 * @param <ID> The ID type
 */
public interface CrudService<T, ID> {
    
    /**
     * Create a new entity
     * Each implementation provides specific creation logic
     * 
     * @param entity The entity to create
     * @return The created entity
     */
    T create(T entity);
    
    /**
     * Find an entity by its ID
     * Each implementation provides specific retrieval logic
     * 
     * @param id The entity ID
     * @return The found entity
     */
    T findById(ID id);
    
    /**
     * Find all entities
     * Each implementation provides specific retrieval logic
     * 
     * @return List of all entities
     */
    List<T> findAll();
    
    /**
     * Update an existing entity
     * Each implementation provides specific update logic
     * 
     * @param id The entity ID
     * @param entity The updated entity data
     * @return The updated entity
     */
    T update(ID id, T entity);
    
    /**
     * Delete an entity by its ID
     * Each implementation provides specific deletion logic
     * 
     * @param id The entity ID to delete
     */
    void delete(ID id);
}
