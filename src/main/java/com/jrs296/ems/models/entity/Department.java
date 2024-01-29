package com.jrs296.ems.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private int departmentID; //generated

    @Column(name = "Name", unique = true)
    private String departmentName; //inputDTO

    @Column(name = "ManagerID")
    private int departmentManagerID = -1; //inputDTO - Needs that Employee ID to exist

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectDepartment", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Project> departmentProjects; //null - to be assigned

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeDepartment", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<Employee> departmentEmployeesUnAssignedToProjects; //null - to be assigned

    public Department () {};
    public Department(String departmentName) {
        this.departmentName = departmentName;
        departmentProjects = new ArrayList<Project>();
        departmentEmployeesUnAssignedToProjects = new ArrayList<Employee>();
    }

    public Department(String departmentName, int departmentManagerID) {
        this.departmentManagerID = departmentManagerID;
        this.departmentName = departmentName;
        departmentProjects = new ArrayList<Project>();
        departmentEmployeesUnAssignedToProjects = new ArrayList<Employee>();
    }
}