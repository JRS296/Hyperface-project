package com.jrs296.ems.controller;

import com.jrs296.ems.exceptions.ResourceNotFoundException;
import com.jrs296.ems.models.DTOs.ProjectDTO;
import com.jrs296.ems.models.entity.Department;
import com.jrs296.ems.models.entity.Project;
import com.jrs296.ems.service.DepartmentService;
import com.jrs296.ems.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/project")
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("")
    public Project saveProject(@Valid @RequestBody ProjectDTO projectDTO) throws ResourceNotFoundException {
        Department projectDepartment = departmentService.getDepartmentById(projectDTO.getProjectDepartmentID());
        Project project = projectService.saveProject(projectDTO.toProject(projectDepartment));
        projectDepartment.getDepartmentProjects().add(project);
        departmentService.saveDepartment(projectDepartment);
        return new ResponseEntity<>(project, HttpStatus.CREATED).getBody();
    }

    @GetMapping("")
    public List<Project> getAllProjects() {
        return projectService.fetchAllProjects();
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable("id") int id) {
        return projectService.getProjectById(id);
    }

    @PutMapping("/{id}")
    public Project updateProject(@PathVariable("id") int id, @RequestBody Project Project) {
        return projectService.updateEmployeeById(id, Project);
    }

    @DeleteMapping("/{id}")
    public String deleteProject(@PathVariable("id") int id) {
        return projectService.deleteProjectById(id);
    }
}
