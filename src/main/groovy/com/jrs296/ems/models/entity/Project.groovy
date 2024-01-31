package com.jrs296.ems.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import groovy.transform.Canonical;
import jakarta.persistence.*;

@Entity
@Canonical
@Table(name = "Projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectID")
    private int projectID; //generated

    @Column(name = "Name", unique = true)
    private String projectName; //inputDTO

    @Column(name = "ManagerID")
    private int projectManagerID = -1; //inputDTO - Needs that Employee ID to exist

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DepartmentID")
    @JsonBackReference
    private Department projectDepartment; //inputDTO - needs that dept to exist

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeProject", cascade = CascadeType.ALL)
    private List<Employee> projectEmployees; //null - to be assigned

    public Project(String ProjectName, int projectManagerID) {
        this.projectName = ProjectName;
        this.projectManagerID = projectManagerID;
    }

    public Project() {};

    public Project(String projectName, int projectManagerID, Department projectDepartment, List<Employee> projectEmployees) {
        this.projectName = projectName;
        this.projectManagerID = projectManagerID;
        this.projectDepartment = projectDepartment;
        this.projectEmployees = projectEmployees;
    }

    public Project(String ProjectName) {
        this.projectName = ProjectName;
    }

    //Getters and Setters
    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getProjectManagerID() {
        return projectManagerID;
    }

    public void setProjectManagerID(int projectManagerID) {
        this.projectManagerID = projectManagerID;
    }

    public Department getProjectDepartment() {
        return projectDepartment;
    }

    public void setProjectDepartment(Department projectDepartment) {
        this.projectDepartment = projectDepartment;
    }

    public List<Employee> getProjectEmployees() {
        return projectEmployees;
    }

    public void setProjectEmployees(List<Employee> projectEmployees) {
        this.projectEmployees = projectEmployees;
    }
}
