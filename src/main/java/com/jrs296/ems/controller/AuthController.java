package com.jrs296.ems.controller;

import com.jrs296.ems.models.DTOs.InputDTOs.EmployeeLoginInputDTO;
import com.jrs296.ems.models.DTOs.InputDTOs.EmployeeRegisterInputDTO;
import com.jrs296.ems.models.entity.Employee;
import com.jrs296.ems.service.EmployeeInfoService;
import com.jrs296.ems.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private EmployeeInfoService employeeInfoService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Open for All User Types
    @GetMapping("/test")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/register") //DONE
    public String addNewUser(@Valid @RequestBody EmployeeRegisterInputDTO employeeRegisterInputDTO) {
        Employee employee = employeeRegisterInputDTO.toEmployee();
        return employeeInfoService.addUser(employee);
    }

    @PostMapping("/login") //DONE
    public String authenticateAndGetToken(@RequestBody EmployeeLoginInputDTO employeeLoginInputDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(employeeLoginInputDTO.getUsername(), employeeLoginInputDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(employeeLoginInputDTO.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @GetMapping("/user/userProfile")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @GetMapping("/manager/managerProfile")
    @PreAuthorize("hasAnyAuthority('ADMIN','PROJECT_MANAGER', 'DEPT_MANAGER')")
    public String managerProfile() {
        return "Welcome to Manager Profile";
    }
}

