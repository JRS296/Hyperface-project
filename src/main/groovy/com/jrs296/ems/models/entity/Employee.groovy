package com.jrs296.ems.models.entity

import groovy.transform.Canonical;
import jakarta.persistence.*;

@Entity
@Table(name = "Employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmployeeID")
    private int employeeId; //generated

    @Column(name = "Name", unique = true)
    private String employeeName; // signUpInputDTO

    @Column(name = "UserName", unique = true)
    private String username;// loginInputDTO signUpInputDTO

    @Column(name = "Email", unique = true)
    private String employeeEmail;// signUpInputDTO

    @Column(name = "Password")
    private String employeePassword; // loginInputDTO signUpInputDTO

    @Column(name = "Salary")
    private float employeeSalary = 0; //null - to be assigned

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ProjectID")
    private Project employeeProject = null; //null - to be assigned

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DepartmentID")
    private Department employeeDepartment = null; //null - to be assigned

    @Column(name = "Role")
    private String role = "USER"; //null - to be assigned

    public Employee(String employeeName, String username, String employeeEmail, String employeePassword) {
        this.employeeName = employeeName;
        this.username = username;
        this.employeePassword = employeePassword;
        this.employeeEmail = employeeEmail;
    }

    public Employee () {}

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}