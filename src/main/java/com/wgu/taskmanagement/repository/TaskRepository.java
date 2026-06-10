package com.wgu.taskmanagement.repository;

import com.wgu.taskmanagement.model.Project;
import com.wgu.taskmanagement.model.Task;
import com.wgu.taskmanagement.model.TaskStatus;
import com.wgu.taskmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Task entity.
 * Provides database access methods for task operations.
 * Spring Data JPA automatically implements this interface.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    /**
     * Search tasks by title or description containing the query string
     * Supports search functionality requirement with multiple row results
     */
    List<Task> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String titleQuery, String descriptionQuery);
    
    /**
     * Find tasks by status
     * Supports filter functionality requirement
     */
    List<Task> findByStatus(TaskStatus status);
    
    /**
     * Find tasks by project
     * Supports filter functionality requirement
     */
    List<Task> findByProject(Project project);
    
    /**
     * Find tasks by project ID
     * Supports filter functionality requirement
     */
    List<Task> findByProjectId(Long projectId);
    
    /**
     * Find tasks assigned to a specific user
     */
    List<Task> findByAssignedTo(User user);
    
    /**
     * Find tasks created by a specific user
     */
    List<Task> findByCreatedBy(User user);
    
    /**
     * Find all tasks ordered by due date
     */
    List<Task> findAllByOrderByDueDateAsc();
    
    /**
     * Find overdue tasks
     * Custom query for finding tasks past due date and not completed
     */
    @Query("SELECT t FROM Task t WHERE t.dueDate < CURRENT_TIMESTAMP AND t.status != 'COMPLETED'")
    List<Task> findOverdueTasks();
    
    /**
     * Count tasks by status
     * Useful for dashboard statistics
     */
    long countByStatus(TaskStatus status);
    
    /**
     * Count tasks by project
     */
    long countByProject(Project project);
}
