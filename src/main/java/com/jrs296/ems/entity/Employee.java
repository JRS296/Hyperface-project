package com.jrs296.ems.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmployeeID")
    private long employeeId;

    @Column(name = "Name")
    private String employeeName;

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

    public Employee(long employeeId, String employeeName, float employeeSalary, Project employeeProject, Department employeeDepartment) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeSalary = employeeSalary;
        this.employeeProject = employeeProject;
        this.employeeDepartment = employeeDepartment;
    }

    public Employee(String employeeName, float employeeSalary) {
        this.employeeName = employeeName;
        this.employeeSalary = employeeSalary;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public float getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(float employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public Project getEmployeeProject() {
        return employeeProject;
    }

    public void setEmployeeProject(Project employeeProject) {
        this.employeeProject = employeeProject;
    }

    public Department getEmployeeDepartment() {
        return employeeDepartment;
    }

    public void setEmployeeDepartment(Department employeeDepartment) {
        this.employeeDepartment = employeeDepartment;
    }
}