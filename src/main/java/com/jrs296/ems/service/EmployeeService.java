package com.jrs296.ems.service;

import com.jrs296.ems.models.entity.Department;
import com.jrs296.ems.models.entity.Employee;
import com.jrs296.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

}
