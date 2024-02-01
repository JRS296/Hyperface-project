package com.jrs296.ems.models.DTOs.OutputDTOs;

import com.jrs296.ems.models.entity.Employee
import groovy.transform.Canonical;

public class EmployeeOutputDTO {
    private int employeeId;

    private String employeeName;

    private String userName;

    private float employeeSalary;

    private int employeeProjectID;

    private int employeeDepartmentID;

    private String role;

    public EmployeeOutputDTO(int employeeId, String employeeName, String userName, float employeeSalary, int employeeProjectID, int employeeDepartmentID, String role) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.userName = userName;
        this.employeeSalary = employeeSalary;
        this.employeeProjectID = employeeProjectID;
        this.employeeDepartmentID = employeeDepartmentID;
        this.role = role;
    }

    public EmployeeOutputDTO() {}

    public static EmployeeOutputDTO toEmployeeOutputDTO(Employee employee) {
        int employeeProjectID = -1;
        int employeeDepartmentID = -1;
        if(employee.getEmployeeProject() != null) employeeProjectID = employee.getEmployeeProject().getProjectID();
        if(employee.getEmployeeDepartment() != null) employeeDepartmentID = employee.getEmployeeDepartment().getDepartmentID();
        return new EmployeeOutputDTO(employee.getEmployeeId(), employee.getEmployeeName(), employee.getUsername(), employee.getEmployeeSalary(), employeeProjectID, employeeDepartmentID, employee.getRole());
    }

    public static List<EmployeeOutputDTO> toListEmployeesOutputDTO(List<Employee> employees) {
        List<EmployeeOutputDTO> outputDTOS = new ArrayList<EmployeeOutputDTO>(employees.size());
        for (Employee employee : employees) {
            outputDTOS.add(toEmployeeOutputDTO(employee));
        }
        return outputDTOS;
    }

    //Getters and Setters
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public float getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(float employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public int getEmployeeProjectID() {
        return employeeProjectID;
    }

    public void setEmployeeProjectID(int employeeProjectID) {
        this.employeeProjectID = employeeProjectID;
    }

    public int getEmployeeDepartmentID() {
        return employeeDepartmentID;
    }

    public void setEmployeeDepartmentID(int employeeDepartmentID) {
        this.employeeDepartmentID = employeeDepartmentID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
