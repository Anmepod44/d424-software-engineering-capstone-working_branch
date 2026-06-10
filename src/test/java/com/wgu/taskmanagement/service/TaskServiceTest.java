package com.wgu.taskmanagement.service;

import com.wgu.taskmanagement.exception.ResourceNotFoundException;
import com.wgu.taskmanagement.exception.ValidationException;
import com.wgu.taskmanagement.model.Task;
import com.wgu.taskmanagement.model.TaskStatus;
import com.wgu.taskmanagement.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for TaskService.
 *
 * Test Plan Coverage:
 *   TC-20: create() saves and returns a valid task
 *   TC-21: create() sets default status PENDING when status is null
 *   TC-22: create() throws ValidationException when title is null
 *   TC-23: create() throws ValidationException when title is blank
 *   TC-24: findById() returns task when found
 *   TC-25: findById() throws ResourceNotFoundException when not found
 *   TC-26: findAll() returns all tasks
 *   TC-27: update() updates fields on existing task
 *   TC-28: update() throws ResourceNotFoundException for unknown id
 *   TC-29: delete() calls repository delete for existing task
 *   TC-30: searchTasks() returns all tasks when query is blank
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("TaskService Unit Tests")
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task sampleTask;

    @BeforeEach
    void setUp() {
        sampleTask = new Task("Sample Task", "Sample description");
        sampleTask.setStatus(TaskStatus.PENDING);
    }

    // TC-20
    @Test
    @DisplayName("TC-20: create() saves and returns a valid task")
    void create_SavesAndReturnsValidTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        Task result = taskService.create(sampleTask);

        assertNotNull(result);
        assertEquals("Sample Task", result.getTitle());
        verify(taskRepository, times(1)).save(sampleTask);
    }

    // TC-21
    @Test
    @DisplayName("TC-21: create() sets default status PENDING when status is null")
    void create_SetsDefaultStatusWhenNull() {
        sampleTask.setStatus(null);
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        taskService.create(sampleTask);

        assertEquals(TaskStatus.PENDING, sampleTask.getStatus());
    }

    // TC-22
    @Test
    @DisplayName("TC-22: create() throws ValidationException when title is null")
    void create_ThrowsValidationExceptionWhenTitleNull() {
        Task invalidTask = new Task(null, "desc");

        assertThrows(ValidationException.class, () -> taskService.create(invalidTask));
        verify(taskRepository, never()).save(any());
    }

    // TC-23
    @Test
    @DisplayName("TC-23: create() throws ValidationException when title is blank")
    void create_ThrowsValidationExceptionWhenTitleBlank() {
        Task invalidTask = new Task("   ", "desc");

        assertThrows(ValidationException.class, () -> taskService.create(invalidTask));
        verify(taskRepository, never()).save(any());
    }

    // TC-24
    @Test
    @DisplayName("TC-24: findById() returns task when found")
    void findById_ReturnsTaskWhenFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));

        Task result = taskService.findById(1L);

        assertNotNull(result);
        assertEquals("Sample Task", result.getTitle());
    }

    // TC-25
    @Test
    @DisplayName("TC-25: findById() throws ResourceNotFoundException when not found")
    void findById_ThrowsResourceNotFoundWhenMissing() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.findById(99L));
    }

    // TC-26
    @Test
    @DisplayName("TC-26: findAll() returns all tasks")
    void findAll_ReturnsAllTasks() {
        Task t2 = new Task("Task 2", "desc2");
        when(taskRepository.findAll()).thenReturn(Arrays.asList(sampleTask, t2));

        List<Task> results = taskService.findAll();

        assertEquals(2, results.size());
    }

    // TC-27
    @Test
    @DisplayName("TC-27: update() updates fields on existing task")
    void update_UpdatesFieldsOnExistingTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        Task updatedData = new Task("Updated Title", "Updated desc");
        updatedData.setStatus(TaskStatus.IN_PROGRESS);

        Task result = taskService.update(1L, updatedData);

        assertEquals("Updated Title", result.getTitle());
        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());
    }

    // TC-28
    @Test
    @DisplayName("TC-28: update() throws ResourceNotFoundException for unknown id")
    void update_ThrowsResourceNotFoundForUnknownId() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        Task updatedData = new Task("Title", "desc");
        assertThrows(ResourceNotFoundException.class, () -> taskService.update(99L, updatedData));
    }

    // TC-29
    @Test
    @DisplayName("TC-29: delete() calls repository delete for existing task")
    void delete_CallsRepositoryDeleteForExistingTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        doNothing().when(taskRepository).delete(sampleTask);

        taskService.delete(1L);

        verify(taskRepository, times(1)).delete(sampleTask);
    }

    // TC-30
    @Test
    @DisplayName("TC-30: searchTasks() returns all tasks when query is blank")
    void searchTasks_ReturnsAllTasksWhenQueryBlank() {
        when(taskRepository.findAll()).thenReturn(List.of(sampleTask));

        List<Task> results = taskService.searchTasks("");

        assertEquals(1, results.size());
        verify(taskRepository, times(1)).findAll();
    }
}
