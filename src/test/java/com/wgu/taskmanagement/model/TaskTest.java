package com.wgu.taskmanagement.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Task entity.
 *
 * Test Plan Coverage:
 *   TC-01: Task constructor sets title and description
 *   TC-02: isValid() returns true for a valid task
 *   TC-03: isValid() returns false when title is null
 *   TC-04: isValid() returns false when title is blank
 *   TC-05: isValid() returns false when title exceeds 200 characters
 *   TC-06: isOverdue() returns true for past due date with non-COMPLETED status
 *   TC-07: isOverdue() returns false when task is COMPLETED even if past due
 *   TC-08: isOverdue() returns false when dueDate is null
 *   TC-09: Default status is PENDING
 *   TC-10: Default priority is MEDIUM
 */
@DisplayName("Task Entity Unit Tests")
class TaskTest {

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task("Sample Task", "Sample description");
    }

    // TC-01
    @Test
    @DisplayName("TC-01: Constructor sets title and description correctly")
    void constructor_SetsFieldsCorrectly() {
        assertEquals("Sample Task", task.getTitle());
        assertEquals("Sample description", task.getDescription());
    }

    // TC-02
    @Test
    @DisplayName("TC-02: isValid() returns true for a valid task")
    void isValid_ReturnsTrueForValidTask() {
        assertTrue(task.isValid());
    }

    // TC-03
    @Test
    @DisplayName("TC-03: isValid() returns false when title is null")
    void isValid_ReturnsFalseWhenTitleIsNull() {
        task.setTitle(null);
        assertFalse(task.isValid());
    }

    // TC-04
    @Test
    @DisplayName("TC-04: isValid() returns false when title is blank")
    void isValid_ReturnsFalseWhenTitleIsBlank() {
        task.setTitle("   ");
        assertFalse(task.isValid());
    }

    // TC-05
    @Test
    @DisplayName("TC-05: isValid() returns false when title exceeds 200 characters")
    void isValid_ReturnsFalseWhenTitleTooLong() {
        task.setTitle("A".repeat(201));
        assertFalse(task.isValid());
    }

    // TC-06
    @Test
    @DisplayName("TC-06: isOverdue() returns true for past due date with non-COMPLETED status")
    void isOverdue_ReturnsTrueWhenPastDueAndNotCompleted() {
        task.setDueDate(LocalDateTime.now().minusDays(1));
        task.setStatus(TaskStatus.PENDING);
        assertTrue(task.isOverdue());
    }

    // TC-07
    @Test
    @DisplayName("TC-07: isOverdue() returns false when task is COMPLETED even if past due")
    void isOverdue_ReturnsFalseWhenCompleted() {
        task.setDueDate(LocalDateTime.now().minusDays(1));
        task.setStatus(TaskStatus.COMPLETED);
        assertFalse(task.isOverdue());
    }

    // TC-08
    @Test
    @DisplayName("TC-08: isOverdue() returns false when dueDate is null")
    void isOverdue_ReturnsFalseWhenDueDateIsNull() {
        task.setDueDate(null);
        assertFalse(task.isOverdue());
    }

    // TC-09
    @Test
    @DisplayName("TC-09: Default status is PENDING")
    void defaultStatus_IsPending() {
        Task newTask = new Task();
        assertEquals(TaskStatus.PENDING, newTask.getStatus());
    }

    // TC-10
    @Test
    @DisplayName("TC-10: Default priority is MEDIUM")
    void defaultPriority_IsMedium() {
        Task newTask = new Task();
        assertEquals(TaskPriority.MEDIUM, newTask.getPriority());
    }
}
