package com.jrs296.ems.models.DTOs.InputDTOs

import groovy.transform.Canonical;
import jakarta.validation.constraints.NotNull;

@Canonical
public class AssignManagerDTO { //Login

    @NotNull(message = "Employee ID is required.")
    private int employeeID;

    @NotNull(message = "Project/Department Role is required.")
    private String assignToRole;

    @NotNull(message = "Project/Department ID is required.")
    private int assignToRoleID;

    public AssignManagerDTO() {}

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getAssignToRole() {
        return assignToRole;
    }

    public void setAssignToRole(String assignToRole) {
        this.assignToRole = assignToRole;
    }

    public int getAssignToRoleID() {
        return assignToRoleID;
    }

    public void setAssignToRoleID(int assignToRoleID) {
        this.assignToRoleID = assignToRoleID;
    }
}