package com.jrs296.ems.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DepartmentID")
    private int departmentID;

    @Column(name = "Name", unique = true)
    private String departmentName;

//    @Column(name = "Projects")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectDepartment", cascade = CascadeType.ALL)
    private List<Project> departmentProjects;

//    @Column(name = "Employees")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeDepartment", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<Employee> departmentEmployees;

    public Department() {
    }

    public Department(String departmentName) {
        this.departmentName = departmentName;
        departmentProjects = new ArrayList<Project>();
        departmentEmployees = new ArrayList<Employee>();
    }

//    public Department(int departmentID, String departmentName, List<Project> departmentProjects, List<Employee> departmentEmployees) {
//        this.departmentID = departmentID;
//        this.departmentName = departmentName;
//        this.departmentProjects = departmentProjects;
//        this.departmentEmployees = departmentEmployees;
//    }

    public String asString() {
        return "String??";
    }
}