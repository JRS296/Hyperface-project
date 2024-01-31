package com.jrs296.ems.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    private List<Employee> allEmployees; //generated

    public Department () {
        departmentProjects = new ArrayList<Project>(3);
        allEmployees = new ArrayList<Employee>(10);
    };
    public Department(String departmentName) {
        this.departmentName = departmentName;
        departmentProjects = new ArrayList<Project>(3);
        allEmployees = new ArrayList<Employee>(10);
    }

    public Department(String departmentName, int departmentManagerID) {
        this.departmentManagerID = departmentManagerID;
        this.departmentName = departmentName;
        departmentProjects = new ArrayList<Project>(3);
        allEmployees = new ArrayList<Employee>(10);
    }
}