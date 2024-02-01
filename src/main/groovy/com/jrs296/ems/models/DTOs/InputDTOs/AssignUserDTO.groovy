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

    public AssignUserDTO() {}

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getAssignToDepartmentID() {
        return assignToDepartmentID;
    }

    public void setAssignToDepartmentID(int assignToDepartmentID) {
        this.assignToDepartmentID = assignToDepartmentID;
    }

    public int getAssignToProjectID() {
        return assignToProjectID;
    }

    public void setAssignToProjectID(int assignToProjectID) {
        this.assignToProjectID = assignToProjectID;
    }
}
