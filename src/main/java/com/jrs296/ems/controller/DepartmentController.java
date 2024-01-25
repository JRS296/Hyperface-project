package com.jrs296.ems.controller;

import com.jrs296.ems.exceptions.ResourceNotFoundException;
import com.jrs296.ems.models.DTOs.InputDTOs.DepartmentInputDTO;
import com.jrs296.ems.models.DTOs.OutputDTOs.DepartmentOutputDTO;
import com.jrs296.ems.models.entity.Department;
import com.jrs296.ems.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/service/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public DepartmentOutputDTO saveDepartment(@Valid @RequestBody DepartmentInputDTO departmentInputDTO) throws ResourceNotFoundException {
        Department department = departmentService.saveDepartment(departmentInputDTO.toDepartment());
        return new ResponseEntity<>(DepartmentOutputDTO.toDepartmentOutputDTO(department), HttpStatus.CREATED).getBody();
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN', 'PROJECT_MANAGER', 'DEPT_MANAGER')")
    public List<DepartmentOutputDTO> getAllDepartments() {
        return DepartmentOutputDTO.toListProjectOutputDTO(departmentService.fetchAllDepartments());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN', 'PROJECT_MANAGER', 'DEPT_MANAGER')")
    public DepartmentOutputDTO getDepartmentById(@PathVariable("id") int id) {
        return DepartmentOutputDTO.toDepartmentOutputDTO(departmentService.getDepartmentById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DEPT_MANAGER')")
    public Department updateDepartment(@PathVariable("id") int id, @RequestBody Department department) {
        return departmentService.updateEmployeeById(id, department);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DEPT_MANAGER')")
    public String deleteDepartment(@PathVariable("id") int id) {
        return departmentService.deleteDepartmentById(id);
    }
}
