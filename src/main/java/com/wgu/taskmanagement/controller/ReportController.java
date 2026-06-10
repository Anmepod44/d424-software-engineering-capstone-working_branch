package com.wgu.taskmanagement.controller;

import com.wgu.taskmanagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Report controller extending BaseController - demonstrates INHERITANCE.
 * Handles report generation with multiple columns, rows, timestamps, and titles.
 */
@Controller
@RequestMapping("/reports")
public class ReportController extends BaseController {
    
    private final ReportService reportService;
    
    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
    
    /**
     * Generate and display task report
     * Demonstrates report requirement: multiple columns, rows, timestamp, title
     */
    @GetMapping("/tasks")
    public String generateTaskReport(Model model) {
        ReportService.TaskReport report = reportService.generateTaskReport();
        model.addAttribute("report", report);
        return "reports/tasks";
    }
    
    @GetMapping("/overdue")
    public String generateOverdueReport(Model model) {
        ReportService.TaskReport report = reportService.generateOverdueTasksReport();
        model.addAttribute("report", report);
        return "reports/tasks";
    }
}
