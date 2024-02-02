package com.jrs296.ems.controller;

import com.jrs296.ems.models.DTOs.InputDTOs.AssignManagerDTO;
import com.jrs296.ems.models.DTOs.InputDTOs.AssignUserDTO;
import com.jrs296.ems.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/assign")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PutMapping("/manager")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DEPT_MANAGER')")
    public String assignManagerRole(@Valid @RequestBody AssignManagerDTO assignManagerDTO) {
        return adminService.setManagerForDepartmentsAndProjects(assignManagerDTO);
    }

    @PutMapping("/employee")
    @PreAuthorize("hasAnyAuthority('ADMIN','PROJECT_MANAGER', 'DEPT_MANAGER')")
    public String assignEmployeeRole(@Valid @RequestBody AssignUserDTO assignUserDTO) {
        return adminService.setEmployeeToDepartmentOrProject(assignUserDTO);
    }
}
