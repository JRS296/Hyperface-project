package com.jrs296.ems.controller;

import com.jrs296.ems.models.DTOs.InputDTOs.DepartmentInputDTO;
import com.jrs296.ems.models.DTOs.OutputDTOs.DepartmentOutputDTO;
import com.jrs296.ems.models.DTOs.OutputDTOs.ResponseObject;
import com.jrs296.ems.models.entity.Department;
import com.jrs296.ems.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<ResponseObject<List<DepartmentOutputDTO>>> getAllDepartments() {
        return ResponseEntity.ok().body(new ResponseObject<>("Retrieved Departments Successfully.", 200, DepartmentOutputDTO.toListDepartmentOutputDTO(departmentService.fetchAllDepartments())));
    }

    @GetMapping("/{id}") // DONE
    public ResponseEntity<ResponseObject<DepartmentOutputDTO>> getDepartmentById(@PathVariable("id") int id) {
        return ResponseEntity.ok().body(new ResponseObject<>("Retrieved Department By ID Successfully.", 200, DepartmentOutputDTO.toDepartmentOutputDTO(departmentService.getDepartmentById(id))));
    }

    // ADMIN ONLY
    @PutMapping("/{id}") // DONE
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseObject<Department>> updateDepartment(@PathVariable("id") int id, @Valid @RequestBody DepartmentInputDTO departmentInputDTO) {
        return ResponseEntity.ok().body(new ResponseObject<>("Department Updated Successfully.", 200, departmentService.updateDepartmentByID(id, departmentInputDTO)));
    }

    @PostMapping("") // DONE
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseObject<DepartmentOutputDTO>>  saveDepartment(@Valid @RequestBody DepartmentInputDTO departmentInputDTO) {
        return ResponseEntity.ok().body(new ResponseObject<>("Department Created Successfully.", 200, DepartmentOutputDTO.toDepartmentOutputDTO(departmentService.saveDepartment(departmentInputDTO.toDepartment()))));
    }

    @DeleteMapping("/{id}") // DONE
    @PreAuthorize("hasAnyAuthority('ADMIN')") //Add functionality such that Dept Manager can do this as well
    public ResponseEntity<ResponseObject<String>> deleteDepartment(@PathVariable("id") int id) {
        // Delete ID, Delete all Projects under ID, Assign all Employees under Dept a null project and null Department
        return ResponseEntity.ok().body(new ResponseObject<>("Department Deleted Successfully.", 200, departmentService.deleteDepartmentById(id)));
    }
}
