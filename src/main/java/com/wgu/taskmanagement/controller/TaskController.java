package com.wgu.taskmanagement.controller;

import com.wgu.taskmanagement.model.Task;
import com.wgu.taskmanagement.model.TaskPriority;
import com.wgu.taskmanagement.model.TaskStatus;
import com.wgu.taskmanagement.service.ProjectService;
import com.wgu.taskmanagement.service.TaskService;
import com.wgu.taskmanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Task controller extending BaseController - demonstrates INHERITANCE.
 * 
 * INHERITANCE: This class extends BaseController to inherit common functionality
 * like error handling, validation, and message handling.
 * 
 * Handles all task-related HTTP requests and provides CRUD operations.
 */
@Controller
@RequestMapping("/tasks")
public class TaskController extends BaseController {
    
    private final TaskService taskService;
    private final ProjectService projectService;
    private final UserService userService;
    
    @Autowired
    public TaskController(TaskService taskService, ProjectService projectService, UserService userService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.userService = userService;
    }
    
    /**
     * List all tasks
     * Supports search functionality with multiple row results
     */
    @GetMapping
    public String listTasks(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) Long projectId,
            Model model) {
        
        List<Task> tasks;
        
        // Apply filters based on parameters
        if (search != null && !search.trim().isEmpty()) {
            tasks = taskService.searchTasks(search);
        } else if (status != null) {
            tasks = taskService.filterByStatus(status);
        } else if (projectId != null) {
            tasks = taskService.filterByProject(projectId);
        } else {
            tasks = taskService.findAll();
        }
        
        model.addAttribute("tasks", tasks);
        model.addAttribute("statuses", TaskStatus.values());
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("search", search);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedProjectId", projectId);
        
        return "tasks/list";
    }
    
    /**
     * Show create task form
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("statuses", TaskStatus.values());
        model.addAttribute("priorities", TaskPriority.values());
        return "tasks/form";
    }
    
    /**
     * Create new task
     * Demonstrates validation functionality
     */
    @PostMapping
    public String createTask(@Valid @ModelAttribute Task task, BindingResult result, Model model) {
        // Use inherited validation method
        if (!validateInput(task) || result.hasErrors()) {
            model.addAttribute("projects", projectService.findAll());
            model.addAttribute("users", userService.findAll());
            model.addAttribute("statuses", TaskStatus.values());
            model.addAttribute("priorities", TaskPriority.values());
            addErrorMessage(model, "Please correct the errors below");
            return "tasks/form";
        }
        
        taskService.create(task);
        addSuccessMessage(model, "Task created successfully");
        return "redirect:/tasks";
    }
    
    /**
     * View task details
     */
    @GetMapping("/{id}")
    public String viewTask(@PathVariable Long id, Model model) {
        Task task = taskService.findById(id);
        model.addAttribute("task", task);
        return "tasks/view";
    }
    
    /**
     * Show edit task form
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Task task = taskService.findById(id);
        model.addAttribute("task", task);
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("statuses", TaskStatus.values());
        model.addAttribute("priorities", TaskPriority.values());
        return "tasks/form";
    }
    
    /**
     * Update existing task
     * Demonstrates validation functionality
     */
    @PostMapping("/{id}")
    public String updateTask(@PathVariable Long id, @Valid @ModelAttribute Task task, 
                            BindingResult result, Model model) {
        if (!validateInput(task) || result.hasErrors()) {
            model.addAttribute("projects", projectService.findAll());
            model.addAttribute("users", userService.findAll());
            model.addAttribute("statuses", TaskStatus.values());
            model.addAttribute("priorities", TaskPriority.values());
            addErrorMessage(model, "Please correct the errors below");
            return "tasks/form";
        }
        
        taskService.update(id, task);
        addSuccessMessage(model, "Task updated successfully");
        return "redirect:/tasks";
    }
    
    /**
     * Delete task
     */
    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id, Model model) {
        taskService.delete(id);
        addSuccessMessage(model, "Task deleted successfully");
        return "redirect:/tasks";
    }
}
