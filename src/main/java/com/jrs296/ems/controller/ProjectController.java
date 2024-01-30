package com.jrs296.ems.controller;

import com.jrs296.ems.exceptions.ResourceNotFoundException;
import com.jrs296.ems.models.DTOs.InputDTOs.ProjectInputDTO;
import com.jrs296.ems.models.DTOs.OutputDTOs.ProjectOutputDTO;
import com.jrs296.ems.models.entity.Department;
import com.jrs296.ems.models.entity.Project;
import com.jrs296.ems.service.DepartmentService;
import com.jrs296.ems.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/service/project")
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;

    @Autowired
    private DepartmentService departmentService;

    // General Access
    @GetMapping("")
    public List<ProjectOutputDTO> getAllProjects() {
        return ProjectOutputDTO.toListProjectOutputDTO(projectService.fetchAllProjects());
    }

    @GetMapping("/{id}")
    public ProjectOutputDTO getProjectById(@PathVariable("id") int id) {
        return ProjectOutputDTO.toProjectOutputDTO(projectService.getProjectById(id));
    }

    //Restricted Access
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DEPT_MANAGER')")
    public ProjectOutputDTO createNewProject(@Valid @RequestBody ProjectInputDTO projectInputDTO) {
        Department projectDepartment = departmentService.getDepartmentById(projectInputDTO.getProjectDepartmentID());
        Project project = projectService.saveProject(projectInputDTO.toProject(projectDepartment));
        projectDepartment.getDepartmentProjects().add(project);
        departmentService.saveDepartment(projectDepartment);
        return new ResponseEntity<>(ProjectOutputDTO.toProjectOutputDTO(project), HttpStatus.CREATED).getBody();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DEPT_MANAGER')")
    public Project updateProject(@PathVariable("id") int id, @Valid @RequestBody ProjectInputDTO projectInputDTO) {
        Department department = departmentService.getDepartmentById(projectInputDTO.getProjectDepartmentID());

        return projectService.updateEmployeeById(id, projectInputDTO.toProject(department));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DEPT_MANAGER')")
    public String deleteProject(@PathVariable("id") int id) {
        // Place all Employees in Unassigned List, each employee's Project to null
        return projectService.deleteProjectById(id);
    }
}
