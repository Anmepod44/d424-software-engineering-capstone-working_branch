package com.wgu.taskmanagement.controller;

import com.wgu.taskmanagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;

/**
 * Report controller extending BaseController - demonstrates INHERITANCE.
 * Handles report generation with multiple columns, rows, timestamps, and titles.
 */
@Controller
@RequestMapping("/reports")
public class ReportController extends BaseController {

    private final ReportService reportService;
    private static final DateTimeFormatter FILE_DATE_FMT =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final DateTimeFormatter DISPLAY_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Display the task report page.
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

    /**
     * Download the full task report as a plain-text (.txt) file.
     */
    @GetMapping("/tasks/download")
    public ResponseEntity<byte[]> downloadTaskReport() {
        ReportService.TaskReport report = reportService.generateTaskReport();

        StringBuilder sb = new StringBuilder();
        String line = "=".repeat(100);
        String dashes = "-".repeat(100);

        // Header
        sb.append(line).append("\n");
        sb.append("  ").append(report.getTitle()).append("\n");
        sb.append("  Generated: ").append(report.getGeneratedAt().format(DISPLAY_FMT)).append("\n");
        sb.append("  Total Tasks: ").append(report.getTaskCount()).append("\n");
        sb.append(line).append("\n\n");

        // Column headers
        sb.append(String.format("%-35s %-15s %-10s %-20s %-22s %-25s %-20s%n",
                "TASK NAME", "STATUS", "PRIORITY", "ASSIGNED USER",
                "DUE DATE", "PROJECT", "CREATED DATE"));
        sb.append(dashes).append("\n");

        // Rows
        if (report.getTasks().isEmpty()) {
            sb.append("  No tasks found.\n");
        } else {
            report.getTasks().forEach(task -> {
                String assignedTo = task.getAssignedTo() != null
                        ? task.getAssignedTo().getUsername() : "Unassigned";
                String dueDate = task.getDueDate() != null
                        ? task.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "No due date";
                String project = task.getProject() != null
                        ? task.getProject().getName() : "No project";
                String createdAt = task.getCreatedAt() != null
                        ? task.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "N/A";
                String title = task.getTitle().length() > 33
                        ? task.getTitle().substring(0, 32) + "…" : task.getTitle();

                sb.append(String.format("%-35s %-15s %-10s %-20s %-22s %-25s %-20s%n",
                        title,
                        task.getStatus().getDisplayName(),
                        task.getPriority().getDisplayName(),
                        assignedTo,
                        dueDate,
                        project.length() > 23 ? project.substring(0, 22) + "…" : project,
                        createdAt));
            });
        }

        sb.append("\n").append(line).append("\n");
        sb.append("  End of Report\n");
        sb.append(line).append("\n");

        byte[] content = sb.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8);
        String filename = "task-report_" + report.getGeneratedAt().format(FILE_DATE_FMT) + ".txt";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(content.length)
                .body(content);
    }
}
