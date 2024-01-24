package com.jrs296.ems.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jrs296.ems.models.entity.Department;
import com.jrs296.ems.models.entity.Employee;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "Projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectID")
    private int projectID; //generated

    @Column(name = "Name", unique = true)
    private String projectName; //inputDTO

    @Column(name = "ManagerID", unique = true)
    private int projectManagerID; //inputDTO - Needs that Employee ID to exist

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

    public Project(String ProjectName) {
        this.projectName = ProjectName;
    }
}
