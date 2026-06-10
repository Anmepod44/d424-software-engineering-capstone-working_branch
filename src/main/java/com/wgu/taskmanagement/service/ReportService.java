package com.wgu.taskmanagement.service;

import com.wgu.taskmanagement.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Service for generating reports.
 * Supports report generation requirement with multiple columns, rows, timestamps, and titles.
 */
@Service
public class ReportService {
    
    private final TaskService taskService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Autowired
    public ReportService(TaskService taskService) {
        this.taskService = taskService;
    }
    
    /**
     * Generate a comprehensive task report
     * Includes: multiple columns (task name, status, priority, assigned user, due date)
     *          multiple rows (all tasks)
     *          timestamp (when report was generated)
     *          descriptive title
     * 
     * @return TaskReport object containing all report data
     */
    public TaskReport generateTaskReport() {
        List<Task> tasks = taskService.findAll();
        LocalDateTime generatedAt = LocalDateTime.now();
        String title = "Task Management System - Comprehensive Task Report";
        
        return new TaskReport(title, generatedAt, tasks);
    }
    
    /**
     * Generate a report for overdue tasks
     */
    public TaskReport generateOverdueTasksReport() {
        List<Task> tasks = taskService.getOverdueTasks();
        LocalDateTime generatedAt = LocalDateTime.now();
        String title = "Task Management System - Overdue Tasks Report";
        
        return new TaskReport(title, generatedAt, tasks);
    }
    
    /**
     * Inner class representing a task report
     * Contains title, timestamp, and task data
     */
    public static class TaskReport {
        private final String title;
        private final LocalDateTime generatedAt;
        private final List<Task> tasks;
        
        public TaskReport(String title, LocalDateTime generatedAt, List<Task> tasks) {
            this.title = title;
            this.generatedAt = generatedAt;
            this.tasks = tasks;
        }
        
        public String getTitle() {
            return title;
        }
        
        public LocalDateTime getGeneratedAt() {
            return generatedAt;
        }
        
        public String getFormattedGeneratedAt() {
            return generatedAt.format(DATE_FORMATTER);
        }
        
        public List<Task> getTasks() {
            return tasks;
        }
        
        public int getTaskCount() {
            return tasks.size();
        }
        
        /**
         * Get report columns
         * Required columns: task name, status, priority, assigned user, due date
         */
        public String[] getColumns() {
            return new String[]{
                "Task Name",
                "Status",
                "Priority",
                "Assigned User",
                "Due Date",
                "Project",
                "Created At"
            };
        }
    }
}
