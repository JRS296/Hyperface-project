package com.jrs296.ems.service;

import com.jrs296.ems.models.DTOs.InputDTOs.EmployeeRegisterInputDTO;
import com.jrs296.ems.models.entity.Department;
import com.jrs296.ems.models.entity.Employee;
import com.jrs296.ems.models.entity.Project;
import com.jrs296.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder encoder;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> fetchAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeByID(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.orElse(null);
    }

    public Employee getEmployeeByUsername(String username) {
        Optional<Employee> employee = employeeRepository.findByUsername(username);
        return employee.orElse(null);
    }

    public String updateEmployeeById(EmployeeRegisterInputDTO employeeRegisterInputDTO) {
        //Check if User in question is accessing the given endpoint
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Employee employee = getEmployeeByUsername(userDetails.getUsername());

        employee.setEmployeeEmail(employeeRegisterInputDTO.getEmployeeEmail());
        employee.setEmployeePassword(encoder.encode(employeeRegisterInputDTO.getPassword()));
        employee.setEmployeeName(employeeRegisterInputDTO.getEmployeeName());
        employee.setUsername(employeeRegisterInputDTO.getUserName());

        employeeRepository.save(employee);

        return "Update: SUCCESS";
    }

    public String deleteEmployeeByID(int id) {
        if (employeeRepository.findById(id).isPresent()) {
            employeeRepository.deleteById(id);
            return "Employee deleted successfully";
        }
        return "No such employee in the database";
    }
}
