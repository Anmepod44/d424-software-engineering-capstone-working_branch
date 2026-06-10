package com.wgu.taskmanagement.service;

import com.wgu.taskmanagement.exception.ResourceNotFoundException;
import com.wgu.taskmanagement.exception.ValidationException;
import com.wgu.taskmanagement.model.Project;
import com.wgu.taskmanagement.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ProjectService.
 *
 * Test Plan Coverage:
 *   TC-31: create() saves and returns a valid project
 *   TC-32: create() throws ValidationException when name is null
 *   TC-33: create() throws ValidationException when name exceeds 100 characters
 *   TC-34: findById() returns project when found
 *   TC-35: findById() throws ResourceNotFoundException when not found
 *   TC-36: findAll() returns all projects
 *   TC-37: delete() calls repository delete for existing project
 *   TC-38: searchByName() returns all projects when query is blank
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ProjectService Unit Tests")
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    private Project sampleProject;

    @BeforeEach
    void setUp() {
        sampleProject = new Project("Sample Project", "Sample description");
    }

    // TC-31
    @Test
    @DisplayName("TC-31: create() saves and returns a valid project")
    void create_SavesAndReturnsValidProject() {
        when(projectRepository.save(any(Project.class))).thenReturn(sampleProject);

        Project result = projectService.create(sampleProject);

        assertNotNull(result);
        assertEquals("Sample Project", result.getName());
        verify(projectRepository, times(1)).save(sampleProject);
    }

    // TC-32
    @Test
    @DisplayName("TC-32: create() throws ValidationException when name is null")
    void create_ThrowsValidationExceptionWhenNameNull() {
        Project invalid = new Project(null, "desc");

        assertThrows(ValidationException.class, () -> projectService.create(invalid));
        verify(projectRepository, never()).save(any());
    }

    // TC-33
    @Test
    @DisplayName("TC-33: create() throws ValidationException when name exceeds 100 characters")
    void create_ThrowsValidationExceptionWhenNameTooLong() {
        Project invalid = new Project("A".repeat(101), "desc");

        assertThrows(ValidationException.class, () -> projectService.create(invalid));
        verify(projectRepository, never()).save(any());
    }

    // TC-34
    @Test
    @DisplayName("TC-34: findById() returns project when found")
    void findById_ReturnsProjectWhenFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(sampleProject));

        Project result = projectService.findById(1L);

        assertNotNull(result);
        assertEquals("Sample Project", result.getName());
    }

    // TC-35
    @Test
    @DisplayName("TC-35: findById() throws ResourceNotFoundException when not found")
    void findById_ThrowsResourceNotFoundWhenMissing() {
        when(projectRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> projectService.findById(99L));
    }

    // TC-36
    @Test
    @DisplayName("TC-36: findAll() returns all projects")
    void findAll_ReturnsAllProjects() {
        Project p2 = new Project("Project 2", "desc2");
        when(projectRepository.findAll()).thenReturn(List.of(sampleProject, p2));

        List<Project> results = projectService.findAll();

        assertEquals(2, results.size());
    }

    // TC-37
    @Test
    @DisplayName("TC-37: delete() calls repository delete for existing project")
    void delete_CallsRepositoryDeleteForExistingProject() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(sampleProject));
        doNothing().when(projectRepository).delete(sampleProject);

        projectService.delete(1L);

        verify(projectRepository, times(1)).delete(sampleProject);
    }

    // TC-38
    @Test
    @DisplayName("TC-38: searchByName() returns all projects when query is blank")
    void searchByName_ReturnsAllProjectsWhenQueryBlank() {
        when(projectRepository.findAll()).thenReturn(List.of(sampleProject));

        List<Project> results = projectService.searchByName("");

        assertEquals(1, results.size());
        verify(projectRepository, times(1)).findAll();
    }
}
