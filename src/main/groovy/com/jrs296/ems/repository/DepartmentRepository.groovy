package com.jrs296.ems.repository;

import com.jrs296.ems.models.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
