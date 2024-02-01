package com.jrs296.ems.models.DTOs.InputDTOs;

import com.jrs296.ems.models.entity.Department
import groovy.transform.Canonical;
import jakarta.validation.constraints.NotNull;

public class DepartmentInputDTO {

    @NotNull(message = "Department Name is required.")
    String departmentName;

    DepartmentInputDTO () {}

    public Department toDepartment() {
        return new Department(departmentName);
    }
}
