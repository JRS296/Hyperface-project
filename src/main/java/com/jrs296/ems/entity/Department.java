package com.jrs296.ems.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DepartmentID")
    private long departmentID;

    @Column(name = "Name")
    private String departmentName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectDepartment", cascade = CascadeType.ALL)
    private List<Project> departmentProjects;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeDepartment", cascade = CascadeType.PERSIST)
    private List<Employee> departmentEmployees;

    public Department() {
    }

    public Department(long departmentID, String departmentName, List<Project> departmentProjects, List<Employee> departmentEmployees) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.departmentProjects = departmentProjects;
        this.departmentEmployees = departmentEmployees;
    }

    public Department(String departmentName) {
        this.departmentName = departmentName;
    }

    public long getDepartmentID() {
        return departmentID;
    }
    public void setDepartmentID(long departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<Project> getDepartmentProjects() { return departmentProjects; }
    public void setDepartmentProjects(List<Project> departmentProjects) { this.departmentProjects = departmentProjects; }

    public List<Employee> getDepartmentEmployees() { return departmentEmployees; }
    public void setDepartmentEmployees(List<Employee> departmentEmployees) { this.departmentEmployees = departmentEmployees; }

    public String asString() {
        return "String??";
    }
}