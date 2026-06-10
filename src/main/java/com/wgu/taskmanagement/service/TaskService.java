package com.wgu.taskmanagement.service;

import com.wgu.taskmanagement.exception.ResourceNotFoundException;
import com.wgu.taskmanagement.exception.ValidationException;
import com.wgu.taskmanagement.model.Task;
import com.wgu.taskmanagement.model.TaskStatus;
import com.wgu.taskmanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Task service implementing CrudService interface - demonstrates POLYMORPHISM.
 * 
 * POLYMORPHISM: This class implements the CrudService<Task, Long> interface,
 * providing Task-specific implementations of the generic CRUD operations.
 * The same interface is implemented differently by UserService and ProjectService.
 */
@Service
@Transactional
public class TaskService implements CrudService<Task, Long> {
    
    private final TaskRepository taskRepository;
    
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    /**
     * Create a new task with validation
     * Polymorphic implementation of CrudService.create()
     */
    @Override
    public Task create(Task task) {
        // Validate task before creation
        validateTask(task);
        
        // Set default status if not provided
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.PENDING);
        }
        
        return taskRepository.save(task);
    }
    
    /**
     * Find task by ID
     * Polymorphic implementation of CrudService.findById()
     */
    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", id));
    }
    
    /**
     * Find all tasks
     * Polymorphic implementation of CrudService.findAll()
     */
    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }
    
    /**
     * Update existing task with validation
     * Polymorphic implementation of CrudService.update()
     */
    @Override
    public Task update(Long id, Task task) {
        Task existing = findById(id);
        
        // Validate updated task data
        validateTask(task);
        
        // Update fields
        existing.setTitle(task.getTitle());
        existing.setDescription(task.getDescription());
        existing.setStatus(task.getStatus());
        existing.setPriority(task.getPriority());
        existing.setDueDate(task.getDueDate());
        existing.setProject(task.getProject());
        existing.setAssignedTo(task.getAssignedTo());
        
        return taskRepository.save(existing);
    }
    
    /**
     * Delete task by ID
     * Polymorphic implementation of CrudService.delete()
     */
    @Override
    public void delete(Long id) {
        Task task = findById(id);
        taskRepository.delete(task);
    }
    
    // Additional business methods specific to TaskService
    
    /**
     * Search tasks by query string
     * Supports search functionality requirement
     */
    public List<Task> searchTasks(String query) {
        if (query == null || query.trim().isEmpty()) {
            return findAll();
        }
        return taskRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                query, query);
    }
    
    /**
     * Filter tasks by status
     * Supports filter functionality requirement
     */
    public List<Task> filterByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }
    
    /**
     * Filter tasks by project ID
     * Supports filter functionality requirement
     */
    public List<Task> filterByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }
    
    /**
     * Get overdue tasks
     */
    public List<Task> getOverdueTasks() {
        return taskRepository.findOverdueTasks();
    }
    
    /**
     * Get tasks ordered by due date
     */
    public List<Task> getTasksByDueDate() {
        return taskRepository.findAllByOrderByDueDateAsc();
    }
    
    /**
     * Validate task data
     * Demonstrates encapsulation of validation logic
     */
    private void validateTask(Task task) {
        Map<String, String> errors = new HashMap<>();
        
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            errors.put("title", "Title is required");
        } else if (task.getTitle().length() > 200) {
            errors.put("title", "Title must not exceed 200 characters");
        }
        
        if (!errors.isEmpty()) {
            throw new ValidationException("Task validation failed", errors);
        }
    }
}
