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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("")
    public DepartmentOutputDTO saveDepartment(@Valid @RequestBody DepartmentInputDTO departmentInputDTO) throws ResourceNotFoundException {
        Department department = departmentService.saveDepartment(departmentInputDTO.toDepartment());
        return new ResponseEntity<>(DepartmentOutputDTO.toDepartmentOutputDTO(department), HttpStatus.CREATED).getBody();
    }

    @GetMapping("")
    public List<DepartmentOutputDTO> getAllDepartments() {
        return DepartmentOutputDTO.toListProjectOutputDTO(departmentService.fetchAllDepartments());
    }

    @GetMapping("/{id}")
    public DepartmentOutputDTO getDepartmentById(@PathVariable("id") int id) {
        return DepartmentOutputDTO.toDepartmentOutputDTO(departmentService.getDepartmentById(id));
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
