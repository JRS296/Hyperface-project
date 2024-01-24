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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/project")
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("")
    public ProjectOutputDTO saveProject(@Valid @RequestBody ProjectInputDTO projectInputDTO) throws ResourceNotFoundException {
        Department projectDepartment;
        try {
            projectDepartment = departmentService.getDepartmentById(projectInputDTO.getProjectDepartmentID());
            Project project = projectService.saveProject(projectInputDTO.toProject(projectDepartment));
            projectDepartment.getDepartmentProjects().add(project);
            departmentService.saveDepartment(projectDepartment);
            return new ResponseEntity<>(ProjectOutputDTO.toProjectOutputDTO(project), HttpStatus.CREATED).getBody();
        } catch (Exception e) {
            System.out.println("Cannot find ID");
        }

        return null;
    }

    @GetMapping("")
    public List<ProjectOutputDTO> getAllProjects() {
        return ProjectOutputDTO.toListProjectOutputDTO(projectService.fetchAllProjects());
    }

    @GetMapping("/{id}")
    public ProjectOutputDTO getProjectById(@PathVariable("id") int id) {
        return ProjectOutputDTO.toProjectOutputDTO(projectService.getProjectById(id));
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
