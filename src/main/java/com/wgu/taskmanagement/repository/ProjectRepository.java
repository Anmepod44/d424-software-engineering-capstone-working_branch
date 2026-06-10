package com.wgu.taskmanagement.repository;

import com.wgu.taskmanagement.model.Project;
import com.wgu.taskmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Project entity.
 * Provides database access methods for project operations.
 * Spring Data JPA automatically implements this interface.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    /**
     * Find projects by owner
     * Used to display user's projects
     */
    List<Project> findByOwner(User owner);
    
    /**
     * Find projects by owner ID
     */
    List<Project> findByOwnerId(Long ownerId);
    
    /**
     * Find projects by name containing (case-insensitive search)
     */
    List<Project> findByNameContainingIgnoreCase(String name);
}
