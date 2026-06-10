package com.wgu.taskmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main application class for WGU Task Management System.
 * 
 * This application demonstrates:
 * - Inheritance: BaseEntity extended by Task, User, Project
 * - Polymorphism: CrudService interface implemented by multiple services
 * - Encapsulation: Private fields with controlled access through getters/setters
 */
@SpringBootApplication
@EnableJpaAuditing
public class TaskManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagementApplication.class, args);
    }
}
