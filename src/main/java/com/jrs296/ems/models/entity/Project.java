package com.jrs296.ems.models.entity;

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
    private long projectID;

    @Column(name = "Name", unique = true)
    private String projectName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DepartmentID")
    @JsonIgnore
    private Department projectDepartment;

    @Transient
    private int projectDepartmentID;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeProject", cascade = CascadeType.ALL)
    private List<Employee> projectEmployees;

    public Project() {
    }

    public Project(int ProjectID, String ProjectName, Department projectDepartment, List<Employee> projectEmployees) {
        this.projectID = ProjectID;
        this.projectName = ProjectName;
        this.projectDepartment = projectDepartment;
        this.projectEmployees = projectEmployees;
    }

    public Project(String ProjectName) {
        this.projectName = ProjectName;
        /*
        Do this in services
        * Owner ownerTemp = ownerRepository.getById(Integer.valueOf(id));
        * blog.setOwner(ownerTemp);
        *  */
    }
}
