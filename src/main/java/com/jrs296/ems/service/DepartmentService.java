package com.jrs296.ems.service;

import com.jrs296.ems.models.DTOs.InputDTOs.DepartmentInputDTO;
import com.jrs296.ems.models.entity.Department;
import com.jrs296.ems.models.entity.Employee;
import com.jrs296.ems.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Objects;


@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeService employeeService;

    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public List<Department> fetchAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(int id) {
        Optional<Department> department = departmentRepository.findById(id);
        return department.orElse(null);
    }

    public Department updateDepartmentByID(int id, DepartmentInputDTO departmentInputDTO) {
        Optional<Department> tempDepartment = departmentRepository.findById(id);

        if (tempDepartment.isPresent()) {
            Department originalEmployee = tempDepartment.get();

            originalEmployee.setDepartmentName(departmentInputDTO.getDepartmentName());

            return departmentRepository.save(originalEmployee);
        }
        throw new RuntimeException("Department specified does not exist");
    }

    public String deleteDepartmentById(int id) {
        if (departmentRepository.findById(id).isPresent()) {
            // Decouple all Employees Associated with this Department
            List<Employee> employees = departmentRepository.findById(id).get().getAllEmployees();
            for (Employee employee : employees) {
                employee.setEmployeeProject(null);
                employee.setEmployeeDepartment(null);
                employee.setRole("USER");
                employeeService.saveEmployee(employee);
            }

            departmentRepository.deleteById(id);
            return "Department deleted successfully (Employees Persisted)";
        }
        return "No such Department in the database";
    }
}
