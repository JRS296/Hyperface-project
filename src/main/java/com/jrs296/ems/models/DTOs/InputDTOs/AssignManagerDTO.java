package com.jrs296.ems.models.DTOs.InputDTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignManagerDTO { //Login

    @NotNull(message = "Employee ID is required.")
    private int employeeID;

    @NotNull(message = "Project/Department Role is required.")
    private String assignToRole;

    @NotNull(message = "Project/Department ID is required.")
    private int assignToRoleID;


}