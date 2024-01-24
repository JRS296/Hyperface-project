package com.jrs296.ems.models.DTOs.InputDTOs;

import com.jrs296.ems.models.entity.Department;
import com.jrs296.ems.models.entity.Employee;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;

@Data
public class EmployeeInputDTO {
    @NotNull(message = "Employee Name is required.")
    private String employeeName;

    @NotNull(message = "User name is required.")
    private String userName;

    @NotNull(message = "Password is required.")
    private String password;

    private int employeeProjectID = -1; //Can be null, if provided, is to be verified along with employeeDepartmentID

    public Employee toEmployee() {
//        if (employeeProjectID != -1) return new Employee(employeeName, userName, password, employeeProjectID); Will need to search projects if its been called
        return new Employee(employeeName, userName, password);
    }
}
