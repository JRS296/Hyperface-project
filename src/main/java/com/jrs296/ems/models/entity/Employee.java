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
    private int employeeId; //generated

    @Column(name = "Name", unique = true)
    private String employeeName; //inputDTO

    @Column(name = "UserName", unique = true)
    private String userName;//inputDTO

    @Column(name = "Password")
    private String employeePassword; //inputDTO

    @Column(name = "Salary")
    private float employeeSalary = 0; //null - to be assigned

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ProjectID")
    private Project employeeProject = null; //null - to be assigned

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DepartmentID")
    private Department employeeDepartment = null; //null - to be assigned

    @Column(name = "IsManager")
    private boolean isManager = false; //null - to be assigned

    public Employee(String employeeName, String userName, String employeePassword) {
        this.employeeName = employeeName;
        this.userName = userName;
        this.employeePassword = employeePassword;
    }

    public Employee(String employeeName, String userName, String employeePassword, Project employeeProject) {
        this.employeeName = employeeName;
        this.userName = userName;
        this.employeePassword = employeePassword;
        this.employeeProject = employeeProject;
    }
}