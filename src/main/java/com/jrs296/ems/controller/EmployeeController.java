package com.jrs296.ems.controller;

import com.jrs296.ems.exceptions.ResourceNotFoundException;
import com.jrs296.ems.models.DTOs.InputDTOs.EmployeeLoginInputDTO;
import com.jrs296.ems.models.DTOs.InputDTOs.EmployeeRegisterInputDTO;
import com.jrs296.ems.models.entity.Employee;
import com.jrs296.ems.service.DepartmentService;
import com.jrs296.ems.service.EmployeeService;
import com.jrs296.ems.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/service/employee")
public class EmployeeController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

//    @PostMapping("")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'PROJECT_MANAGER', 'DEPT_MANAGER')")
//    public Employee registerEmployee(@Valid @RequestBody EmployeeRegisterInputDTO employeeRegisterInputDTO) {
//        Department projectDepartment;
//        try {
//            projectDepartment = departmentService.getDepartmentById(projectInputDTO.getProjectDepartmentID());
//            Project project = projectService.saveProject(projectInputDTO.toProject(projectDepartment));
//            projectDepartment.getDepartmentProjects().add(project);
//            departmentService.saveDepartment(projectDepartment);
//            return new ResponseEntity<>(ProjectOutputDTO.toProjectOutputDTO(project), HttpStatus.CREATED).getBody();
//        } catch (Exception e) {
//            System.out.println("Cannot find ID");
//        }
//
//        Employee employee = employeeRegisterInputDTO.toEmployee();
//
//        employeeService.saveEmployee(employee);
//        return new ResponseEntity<>(EmployeeOutputDTO.toEmployeeOutputDTO(employee), HttpStatus.CREATED).getBody();
//        return new ResponseEntity<>(employee, HttpStatus.CREATED).getBody();
//
//    }
//
//    @GetMapping("")
//    @PreAuthorize("hasAnyAuthority('USER','ADMIN', 'PROJECT_MANAGER', 'DEPT_MANAGER')")
//    public List<ProjectOutputDTO> getAllProjects() {
//        return ProjectOutputDTO.toListProjectOutputDTO(projectService.fetchAllProjects());
//    }
//
//    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('USER','ADMIN', 'PROJECT_MANAGER', 'DEPT_MANAGER')")
//    public ProjectOutputDTO getProjectById(@PathVariable("id") int id) {
//        return ProjectOutputDTO.toProjectOutputDTO(projectService.getProjectById(id));
//    }
//
//    @PutMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('USER','ADMIN', 'PROJECT_MANAGER', 'DEPT_MANAGER')")
//    public Project updateProject(@PathVariable("id") int id, @RequestBody Project Project) {
//        return projectService.updateEmployeeById(id, Project);
//    }
//
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'PROJECT_MANAGER', 'DEPT_MANAGER')")
//    public String deleteProject(@PathVariable("id") int id) {
//        return projectService.deleteProjectById(id);
//    }
}
