package com.jrs296.ems.service;

import com.jrs296.ems.models.entity.Department;
import com.jrs296.ems.models.entity.Employee;
import com.jrs296.ems.models.entity.Project;
import com.jrs296.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

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

//    public Employee updateEmployeeById(int id, Project Project) {
//        Optional<Employee> tempEmployee = employeeRepository.findById(id);
//
//        if (tempEmployee.isPresent()) {
//            Employee originalEmployee = tempEmployee.get();
//
//            if (Objects.nonNull(Project.getProjectName()) && !"".equalsIgnoreCase(Project.getProjectName())) {
//                originalEmployee.setProjectName(Project.getProjectName());
//            }
//            if (Objects.nonNull(Project.getProjectEmployees())) {
//                originalEmployee.setProjectEmployees(Project.getProjectEmployees());
//            }
////            if (Objects.nonNull(Project.getProjectDepartment())) {
////                originalEmployee.setProjectDepartment(Project.getProjectDepartment());
////            }
//
//            return employeeRepository.save(originalEmployee);
//        }
//        return null;
//    }

    public String deleteEmployeeByID(int id) {
        if (employeeRepository.findById(id).isPresent()) {
            employeeRepository.deleteById(id);
            return "Employee deleted successfully";
        }
        return "No such employee in the database";
    }
}
