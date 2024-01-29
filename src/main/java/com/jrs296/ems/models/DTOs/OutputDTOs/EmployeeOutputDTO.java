package com.jrs296.ems.models.DTOs.OutputDTOs;

import com.jrs296.ems.models.entity.Employee;
import com.jrs296.ems.models.entity.Project;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
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

    public static EmployeeOutputDTO toEmployeeOutputDTO(Employee employee) {
        return new EmployeeOutputDTO(employee.getEmployeeId(), employee.getEmployeeName(), employee.getUsername(), employee.getEmployeeSalary(), employee.getEmployeeProject().getProjectID(), employee.getEmployeeDepartment().getDepartmentID(), employee.getRole());
    }

    public static List<EmployeeOutputDTO> toListEmployeesOutputDTO(List<Employee> employees) {
        List<EmployeeOutputDTO> outputDTOS = new ArrayList<EmployeeOutputDTO>(employees.size());
        for (Employee employee : employees) {
            outputDTOS.add(toEmployeeOutputDTO(employee));
        }
        return outputDTOS;
    }
}
