package com.jrs296.ems.models.DTOs.InputDTOs;

import com.jrs296.ems.models.entity.Department;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;

@Data
public class DepartmentInputDTO {

    @NotNull(message = "Department Name is required.")
    private String departmentName;

    public Department toDepartment() {
        return new Department(departmentName);
    }
}
