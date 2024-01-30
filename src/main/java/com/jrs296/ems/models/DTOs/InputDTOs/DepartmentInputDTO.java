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
        Department temp = new Department(departmentName);
        temp.setAllEmployees(new ArrayList<>(10));
        temp.setUnAssignedToProject(new ArrayList<>(10));
        temp.setDepartmentProjects(new ArrayList<>(3));
        return temp;
    }
}
