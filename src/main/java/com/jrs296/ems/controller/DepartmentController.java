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

    // Auth + General Access
    @GetMapping("") // DONE
    public List<DepartmentOutputDTO> getAllDepartments() {
        return DepartmentOutputDTO.toListProjectOutputDTO(departmentService.fetchAllDepartments());
    }

    @GetMapping("/{id}") // DONE
    public DepartmentOutputDTO getDepartmentById(@PathVariable("id") int id) {
        return DepartmentOutputDTO.toDepartmentOutputDTO(departmentService.getDepartmentById(id));
    }

    // ADMIN ONLY
    @PutMapping("/{id}") // DONE
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public DepartmentOutputDTO updateDepartment(@PathVariable("id") int id, @Valid @RequestBody DepartmentInputDTO departmentInputDTO) {
        Department department = departmentService.updateEmployeeById(id, departmentInputDTO.toDepartment());
        return new ResponseEntity<>(DepartmentOutputDTO.toDepartmentOutputDTO(department), HttpStatus.CREATED).getBody();
    }

    @PostMapping("") // DONE
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public DepartmentOutputDTO saveDepartment(@Valid @RequestBody DepartmentInputDTO departmentInputDTO) throws ResourceNotFoundException {
        Department department = departmentService.saveDepartment(departmentInputDTO.toDepartment());
        return new ResponseEntity<>(DepartmentOutputDTO.toDepartmentOutputDTO(department), HttpStatus.CREATED).getBody();
    }

    @DeleteMapping("/{id}") // DONE
    @PreAuthorize("hasAnyAuthority('ADMIN')") //Add functionality such that Dept Manager can do this as well
    public String deleteDepartment(@PathVariable("id") int id) {
        // Delete ID, Delete all Projects under ID, Assign all Employees under Dept a null project and null Department
        return departmentService.deleteDepartmentById(id);
    }
}
