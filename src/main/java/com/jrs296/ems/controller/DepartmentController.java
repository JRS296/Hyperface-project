package com.jrs296.ems.controller;

import com.jrs296.ems.exceptions.ResourceNotFoundException;
import com.jrs296.ems.models.DTOs.DepartmentDTO;
import com.jrs296.ems.models.entity.Department;
import com.jrs296.ems.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/display")
    public String displayDepartment(@RequestBody Department department) {
        return departmentService.displayData(department);
    }

    @PostMapping("")
    public Department saveDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) throws ResourceNotFoundException {
        Department department = departmentService.saveDepartment(departmentDTO.toDepartment());
        return new ResponseEntity<>(department, HttpStatus.CREATED).getBody();
    }

    @GetMapping("")
    public List<Department> getAllDepartments() {
        return departmentService.fetchAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable("id") int id) {
        return departmentService.getDepartmentById(id);
    }

    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable("id") int id, @RequestBody Department department) {
        return departmentService.updateEmployeeById(id, department);
    }

    @DeleteMapping("/{id}")
    public String deleteDepartment(@PathVariable("id") int id) {
        return departmentService.deleteDepartmentById(id);
    }
}
