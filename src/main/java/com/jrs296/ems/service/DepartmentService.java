package com.jrs296.ems.service;

import com.jrs296.ems.models.entity.Department;
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

    public Department updateEmployeeById(int id, Department department) {
        Optional<Department> tempDepartment = departmentRepository.findById(id);

        if (tempDepartment.isPresent()) {
            Department originalEmployee = tempDepartment.get();

            if (Objects.nonNull(department.getDepartmentName()) && !"".equalsIgnoreCase(department.getDepartmentName())) {
                originalEmployee.setDepartmentName(department.getDepartmentName());
            }
//            if (Objects.nonNull(department.getDepartmentEmployees())) {
//                originalEmployee.setDepartmentEmployees(department.getDepartmentEmployees());
//            }
//            if (Objects.nonNull(department.getDepartmentProjects())) {
//                originalEmployee.setDepartmentProjects(department.getDepartmentProjects());
//            }

            return departmentRepository.save(originalEmployee);
        }
        return null;
    }

    public String deleteDepartmentById(int id) {
        if (departmentRepository.findById(id).isPresent()) {
            departmentRepository.deleteById(id);
            return "Employee deleted successfully";
        }
        return "No such employee in the database";
    }
}
