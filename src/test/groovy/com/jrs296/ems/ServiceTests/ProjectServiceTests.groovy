package com.jrs296.ems.ServiceTests

import com.jrs296.ems.models.DTOs.InputDTOs.ProjectInputDTO;
import com.jrs296.ems.models.entity.Department;
import com.jrs296.ems.models.entity.Project;
import com.jrs296.ems.repository.ProjectRepository
import com.jrs296.ems.service.DepartmentService
import com.jrs296.ems.service.EmployeeService
import com.jrs296.ems.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTests {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private DepartmentService departmentService;

    @Mock
    private EmployeeService employeeService;

    @Test
    void saveProjectInputDTO_ValidProjectInputDTO_ReturnsSavedProject() {
        // Arrange
        ProjectInputDTO projectInputDTO = new ProjectInputDTO();
        projectInputDTO.setProjectName("Test Project");
        projectInputDTO.setProjectDepartmentID(1);

        Department department = new Department();
        when(departmentService.getDepartmentById(projectInputDTO.getProjectDepartmentID())).thenReturn(department);

        Project savedProject = new Project();
        when(projectRepository.save(any())).thenReturn(savedProject);

        // Act
        Project result = projectService.saveProjectInputDTO(projectInputDTO);

        // Assert
        assertNotNull(result);
        assertEquals(savedProject, result);
        assertTrue(department.getDepartmentProjects().contains(result));
        verify(departmentService, times(1)).saveDepartment(department);
    }

    // Add more test cases for saveProjectInputDTO to cover scenarios with invalid department ID

    @Test
    void saveProject_ValidProject_ReturnsSavedProject() {
        // Arrange
        Project project = new Project();
        when(projectRepository.save(project)).thenReturn(project);

        // Act
        Project result = projectService.saveProject(project);

        // Assert
        assertNotNull(result);
        assertEquals(project, result);
    }

    // Add more test cases for saveProject to cover different scenarios

    @Test
    void fetchAllProjects_NoProjects_ReturnsEmptyList() {
        // Arrange
        when(projectRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        Iterable<Project> projects = projectService.fetchAllProjects();

        // Assert
        assertNotNull(projects);
        assertFalse(projects.iterator().hasNext());
    }

    // Add more test cases for fetchAllProjects to cover scenarios with projects

    @Test
    void getProjectById_ExistingId_ReturnsProject() {
        // Arrange
        int projectId = 1;
        Project project = new Project();
        project.setProjectID(projectId);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // Act
        Project foundProject = projectService.getProjectById(projectId);

        // Assert
        assertNotNull(foundProject);
        assertEquals(projectId, foundProject.getProjectID());
    }

    // Add more test cases for getProjectById to cover scenarios with non-existing project ID

    // Add tests for updateProjectById

    // Add tests for deleteProjectById
}
