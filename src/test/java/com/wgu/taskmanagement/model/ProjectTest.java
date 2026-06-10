package com.wgu.taskmanagement.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Project entity.
 *
 * Test Plan Coverage:
 *   TC-11: Constructor sets name and description
 *   TC-12: getTaskCount() returns 0 for a new project
 *   TC-13: addTask() increases task count
 *   TC-14: removeTask() decreases task count
 *   TC-15: getCompletedTaskCount() counts only COMPLETED tasks
 *   TC-16: getCompletionPercentage() returns 0.0 when no tasks
 *   TC-17: getCompletionPercentage() calculates correctly with mixed statuses
 *   TC-18: addTask() sets bidirectional relationship on task
 *   TC-19: removeTask() clears project reference on task
 */
@DisplayName("Project Entity Unit Tests")
class ProjectTest {

    private Project project;

    @BeforeEach
    void setUp() {
        project = new Project("Test Project", "Test description");
    }

    // TC-11
    @Test
    @DisplayName("TC-11: Constructor sets name and description correctly")
    void constructor_SetsFieldsCorrectly() {
        assertEquals("Test Project", project.getName());
        assertEquals("Test description", project.getDescription());
    }

    // TC-12
    @Test
    @DisplayName("TC-12: getTaskCount() returns 0 for a new project")
    void getTaskCount_ReturnsZeroForNewProject() {
        assertEquals(0, project.getTaskCount());
    }

    // TC-13
    @Test
    @DisplayName("TC-13: addTask() increases task count")
    void addTask_IncreasesTaskCount() {
        Task task = new Task("Task 1", "Desc");
        project.addTask(task);
        assertEquals(1, project.getTaskCount());
    }

    // TC-14
    @Test
    @DisplayName("TC-14: removeTask() decreases task count")
    void removeTask_DecreasesTaskCount() {
        Task task = new Task("Task 1", "Desc");
        project.addTask(task);
        project.removeTask(task);
        assertEquals(0, project.getTaskCount());
    }

    // TC-15
    @Test
    @DisplayName("TC-15: getCompletedTaskCount() counts only COMPLETED tasks")
    void getCompletedTaskCount_CountsOnlyCompleted() {
        Task t1 = new Task("T1", "");
        t1.setStatus(TaskStatus.COMPLETED);
        Task t2 = new Task("T2", "");
        t2.setStatus(TaskStatus.PENDING);
        Task t3 = new Task("T3", "");
        t3.setStatus(TaskStatus.COMPLETED);

        project.addTask(t1);
        project.addTask(t2);
        project.addTask(t3);

        assertEquals(2, project.getCompletedTaskCount());
    }

    // TC-16
    @Test
    @DisplayName("TC-16: getCompletionPercentage() returns 0.0 when no tasks")
    void getCompletionPercentage_ReturnsZeroWhenNoTasks() {
        assertEquals(0.0, project.getCompletionPercentage(), 0.001);
    }

    // TC-17
    @Test
    @DisplayName("TC-17: getCompletionPercentage() calculates correctly with mixed statuses")
    void getCompletionPercentage_CalculatesCorrectly() {
        Task t1 = new Task("T1", "");
        t1.setStatus(TaskStatus.COMPLETED);
        Task t2 = new Task("T2", "");
        t2.setStatus(TaskStatus.PENDING);

        project.addTask(t1);
        project.addTask(t2);

        assertEquals(50.0, project.getCompletionPercentage(), 0.001);
    }

    // TC-18
    @Test
    @DisplayName("TC-18: addTask() sets bidirectional relationship on task")
    void addTask_SetsBidirectionalRelationship() {
        Task task = new Task("Task 1", "Desc");
        project.addTask(task);
        assertSame(project, task.getProject());
    }

    // TC-19
    @Test
    @DisplayName("TC-19: removeTask() clears project reference on task")
    void removeTask_ClearsProjectReferenceOnTask() {
        Task task = new Task("Task 1", "Desc");
        project.addTask(task);
        project.removeTask(task);
        assertNull(task.getProject());
    }
}
