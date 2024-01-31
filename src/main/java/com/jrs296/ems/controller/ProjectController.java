package com.jrs296.ems.controller;

import com.jrs296.ems.models.DTOs.InputDTOs.ProjectInputDTO;
import com.jrs296.ems.models.DTOs.OutputDTOs.ProjectOutputDTO;
import com.jrs296.ems.models.DTOs.OutputDTOs.ResponseObject;
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

    // Auth + General Access
    @GetMapping("") //DONE
    public ResponseEntity<ResponseObject<List<ProjectOutputDTO>>> getAllProjects() {
        return ResponseEntity.ok().body(new ResponseObject<>("Retrieved All Projects Successfully.", 200, ProjectOutputDTO.toListProjectOutputDTO(projectService.fetchAllProjects())));
    }

    @GetMapping("/{id}") //DONE
    public ResponseEntity<ResponseObject<ProjectOutputDTO>> getProjectById(@PathVariable("id") int id) {
        return ResponseEntity.ok().body(new ResponseObject<>("Retrieved Project By ID Successfully.", 200, ProjectOutputDTO.toProjectOutputDTO(projectService.getProjectById(id))));
    }

    // ADMIN and DEPT_MANAGERS ONLY
    @PostMapping("") // ADMIN DONE
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DEPT_MANAGER')")
    public ResponseEntity<ResponseObject<ProjectOutputDTO>> createNewProject(@Valid @RequestBody ProjectInputDTO projectInputDTO) {
        return ResponseEntity.ok().body(new ResponseObject<>("Project Created Successfully.", 200, ProjectOutputDTO.toProjectOutputDTO(projectService.saveProjectInputDTO(projectInputDTO))));
    }

    @PutMapping("/{id}") // ADMIN DONE
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DEPT_MANAGER')")
    public ResponseEntity<ResponseObject<ProjectOutputDTO>> updateProject(@PathVariable("id") int id, @Valid @RequestBody ProjectInputDTO projectInputDTO) {
        return ResponseEntity.ok().body(new ResponseObject<>("Project Updated Successfully.", 200, ProjectOutputDTO.toProjectOutputDTO(projectService.updateProjectById(id, projectInputDTO))));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DEPT_MANAGER')")
    public ResponseEntity<ResponseObject<String>> deleteProject(@PathVariable("id") int id) {
        // Place all Employees in Unassigned List, each employee's Project to null
        return ResponseEntity.ok().body(new ResponseObject<>("Project Deleted Successfully.", 200, projectService.deleteProjectById(id)));
    }
}
