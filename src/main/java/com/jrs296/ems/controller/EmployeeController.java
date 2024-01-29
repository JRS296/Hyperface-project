package com.jrs296.ems.controller;

import com.jrs296.ems.exceptions.ResourceNotFoundException;
import com.jrs296.ems.models.DTOs.InputDTOs.EmployeeLoginInputDTO;
import com.jrs296.ems.models.DTOs.InputDTOs.EmployeeRegisterInputDTO;
import com.jrs296.ems.models.DTOs.OutputDTOs.EmployeeOutputDTO;
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

import java.util.List;

@RestController
@RequestMapping("api/service/employee")
public class EmployeeController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    // GENERAL ACCESS
    @GetMapping("") //DONE
    public List<EmployeeOutputDTO> getAllEmployees() {
        return EmployeeOutputDTO.toListEmployeesOutputDTO(employeeService.fetchAllEmployees());
    }

    @GetMapping("/{id}") //DONE
    public EmployeeOutputDTO getProjectById(@PathVariable("id") int id) {
        return EmployeeOutputDTO.toEmployeeOutputDTO(employeeService.getEmployeeByID(id));
    }

    // Get Employees that are unassigned to any projects

    // Get All Employees that are completely unassigned



    // EMPLOYEE SPECIFIC CHANGES - can edit password, username and name; NOT Dept ID and Project ID
//    @PutMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('USER')")
//    public Employee updateProject(@PathVariable("id") int id, @RequestBody Employee Project) {
//        return projectService.updateEmployeeById(id, Project);
//    }

    // EMPLOYEE SPECIFIC CHANGES - Only Managers (of that Project/Dept) and Admins can remove Employees
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PROJECT_MANAGER', 'DEPT_MANAGER')")
    public String deleteProject(@PathVariable("id") int id) {
        return projectService.deleteProjectById(id);
    }
}
