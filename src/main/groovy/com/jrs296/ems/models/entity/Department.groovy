package com.jrs296.ems.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference
import groovy.transform.Canonical;
import jakarta.persistence.*;

@Entity
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

    // Getters and Setters
    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getDepartmentManagerID() {
        return departmentManagerID;
    }

    public void setDepartmentManagerID(int departmentManagerID) {
        this.departmentManagerID = departmentManagerID;
    }

    public List<Project> getDepartmentProjects() {
        return departmentProjects;
    }

    public void setDepartmentProjects(List<Project> departmentProjects) {
        this.departmentProjects = departmentProjects;
    }

    public List<Employee> getAllEmployees() {
        return allEmployees;
    }

    public void setAllEmployees(List<Employee> allEmployees) {
        this.allEmployees = allEmployees;
    }
}