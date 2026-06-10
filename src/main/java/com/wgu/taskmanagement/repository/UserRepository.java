package com.wgu.taskmanagement.repository;

import com.wgu.taskmanagement.model.User;
import com.wgu.taskmanagement.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity.
 * Provides database access methods for user operations.
 * Spring Data JPA automatically implements this interface.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find user by username
     * Used for authentication
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Find user by email
     * Used for user lookup and validation
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Check if username exists
     * Used for validation during registration
     */
    boolean existsByUsername(String username);
    
    /**
     * Check if email exists
     * Used for validation during registration
     */
    boolean existsByEmail(String email);
    
    /**
     * Find users by role
     * Used for admin user management
     */
    List<User> findByRole(UserRole role);
}
