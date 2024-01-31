package com.jrs296.ems.models.DTOs.InputDTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignUserDTO { //Login

    @NotNull(message = "Employee ID is required.")
    private int employeeID;

    @NotNull(message = "Department ID is required.")
    private int assignToDepartmentID;

    private int assignToProjectID = -1;
}
