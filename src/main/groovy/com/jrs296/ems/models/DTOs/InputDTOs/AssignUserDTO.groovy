package com.jrs296.ems.models.DTOs.InputDTOs

import groovy.transform.Canonical;
import jakarta.validation.constraints.NotNull;

@Canonical
public class AssignUserDTO { //Login

    @NotNull(message = "Employee ID is required.")
    private int employeeID;

    @NotNull(message = "Department ID is required.")
    private int assignToDepartmentID;

    private int assignToProjectID = -1;
}
