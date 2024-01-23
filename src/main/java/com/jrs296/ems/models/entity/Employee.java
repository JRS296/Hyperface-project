package com.jrs296.ems.models.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmployeeID")
    private int employeeId;

    @Column(name = "Name", unique = true)
    private String employeeName;

    @Column(name = "UserName", unique = true)
    private String userName;

    @Column(name = "Password")
    private String employeePassword;

    @Column(name = "Salary")
    private float employeeSalary;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ProjectID")
    private Project employeeProject;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DepartmentID")
    private Department employeeDepartment;

    public Employee() {
    }

    public Employee(int employeeId, String employeeName, float employeeSalary, Project employeeProject, Department employeeDepartment, String userName, String employeePassword) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeSalary = employeeSalary;
        this.employeeProject = employeeProject;
        this.employeeDepartment = employeeDepartment;
        this.userName = userName;
        this.employeePassword = employeePassword;
    }

    public Employee(String employeeName, float employeeSalary, String userName, String employeePassword) {
        this.employeeName = employeeName;
        this.employeeSalary = employeeSalary;
        this.userName = userName;
        this.employeePassword = employeePassword;
    }
}