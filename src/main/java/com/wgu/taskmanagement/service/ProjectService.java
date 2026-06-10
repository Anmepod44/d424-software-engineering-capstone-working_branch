package com.wgu.taskmanagement.service;

import com.wgu.taskmanagement.exception.ResourceNotFoundException;
import com.wgu.taskmanagement.exception.ValidationException;
import com.wgu.taskmanagement.model.Project;
import com.wgu.taskmanagement.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project service implementing CrudService interface - demonstrates POLYMORPHISM.
 * 
 * POLYMORPHISM: This class implements the CrudService<Project, Long> interface,
 * providing Project-specific implementations of the generic CRUD operations.
 * The same interface is implemented differently by TaskService and UserService.
 */
@Service
@Transactional
public class ProjectService implements CrudService<Project, Long> {
    
    private final ProjectRepository projectRepository;
    
    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    
    /**
     * Create a new project with validation
     * Polymorphic implementation of CrudService.create()
     */
    @Override
    public Project create(Project project) {
        validateProject(project);
        return projectRepository.save(project);
    }
    
    /**
     * Find project by ID
     * Polymorphic implementation of CrudService.findById()
     */
    @Override
    public Project findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project", id));
    }
    
    /**
     * Find all projects
     * Polymorphic implementation of CrudService.findAll()
     */
    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }
    
    /**
     * Update existing project with validation
     * Polymorphic implementation of CrudService.update()
     */
    @Override
    public Project update(Long id, Project project) {
        Project existing = findById(id);
        
        validateProject(project);
        
        existing.setName(project.getName());
        existing.setDescription(project.getDescription());
        existing.setOwner(project.getOwner());
        
        return projectRepository.save(existing);
    }
    
    /**
     * Delete project by ID
     * Polymorphic implementation of CrudService.delete()
     */
    @Override
    public void delete(Long id) {
        Project project = findById(id);
        projectRepository.delete(project);
    }
    
    // Additional business methods specific to ProjectService
    
    /**
     * Find projects by owner ID
     */
    public List<Project> findByOwnerId(Long ownerId) {
        return projectRepository.findByOwnerId(ownerId);
    }
    
    /**
     * Search projects by name
     */
    public List<Project> searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return findAll();
        }
        return projectRepository.findByNameContainingIgnoreCase(name);
    }
    
    /**
     * Validate project data
     * Demonstrates encapsulation of validation logic
     */
    private void validateProject(Project project) {
        Map<String, String> errors = new HashMap<>();
        
        if (project.getName() == null || project.getName().trim().isEmpty()) {
            errors.put("name", "Project name is required");
        } else if (project.getName().length() > 100) {
            errors.put("name", "Project name must not exceed 100 characters");
        }
        
        if (!errors.isEmpty()) {
            throw new ValidationException("Project validation failed", errors);
        }
    }
}
