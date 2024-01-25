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
    private String employeeName; // signUpInputDTO

    @Column(name = "UserName", unique = true)
    private String userName;// loginInputDTO signUpInputDTO

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

//    public Employee(String employeeName, String userName, String employeePassword) {
//        this.employeeName = employeeName;
//        this.userName = userName;
//        this.employeePassword = employeePassword;
//    }

    public Employee(String employeeName, String userName, String employeeEmail, String employeePassword) {
        this.employeeName = employeeName;
        this.userName = userName;
        this.employeePassword = employeePassword;
        this.employeeEmail = employeeEmail;
    }

    public Employee () {
    }
}