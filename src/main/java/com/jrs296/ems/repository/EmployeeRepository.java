package com.jrs296.ems.repository;

import com.jrs296.ems.models.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
