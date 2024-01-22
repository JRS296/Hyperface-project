package com.jrs296.ems.service;

import com.jrs296.ems.entity.Department;
import com.jrs296.ems.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public String displayData(Department department) {

        return department.asString();
    }

    public String deleteDepartmentById(int id) {
        if (departmentRepository.findById(id).isPresent()) {
            departmentRepository.deleteById(id);
            return "Employee deleted successfully";
        }
        return "No such employee in the database";
    }
}
