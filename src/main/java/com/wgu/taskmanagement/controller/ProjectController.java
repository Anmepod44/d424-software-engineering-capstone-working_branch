package com.wgu.taskmanagement.controller;

import com.wgu.taskmanagement.model.Project;
import com.wgu.taskmanagement.service.ProjectService;
import com.wgu.taskmanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Project controller extending BaseController - demonstrates INHERITANCE.
 * Inherits common functionality from BaseController.
 */
@Controller
@RequestMapping("/projects")
public class ProjectController extends BaseController {
    
    private final ProjectService projectService;
    private final UserService userService;
    
    @Autowired
    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }
    
    @GetMapping
    public String listProjects(Model model) {
        model.addAttribute("projects", projectService.findAll());
        return "projects/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("users", userService.findAll());
        return "projects/form";
    }
    
    @PostMapping
    public String createProject(@Valid @ModelAttribute Project project, BindingResult result, Model model) {
        if (!validateInput(project) || result.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            return "projects/form";
        }
        projectService.create(project);
        return "redirect:/projects";
    }
    
    @GetMapping("/{id}")
    public String viewProject(@PathVariable Long id, Model model) {
        Project project = projectService.findById(id);
        model.addAttribute("project", project);
        return "projects/view";
    }
}
