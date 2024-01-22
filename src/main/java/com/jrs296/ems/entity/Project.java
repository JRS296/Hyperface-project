package com.jrs296.ems.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectID")
    private long projectID;

    @Column(name = "Name")
    private String projectName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DepartmentID")
    private Department projectDepartment;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeProject", cascade = CascadeType.ALL)
    private List<Employee> projectEmployees;

    public Project() {
    }

    public Project(long ProjectID, String ProjectName, Department projectDepartment, List<Employee> projectEmployees) {
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

    public long getProjectID() {
        return projectID;
    }
    public void setProjectID(long ProjectID) {
        this.projectID = ProjectID;
    }

    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String ProjectName) {
        this.projectName = ProjectName;
    }

    public Department getProjectDepartment() { return projectDepartment; }
    public void setProjectDepartment(Department projectDepartment) { this.projectDepartment = projectDepartment; }

    public List<Employee> getProjectEmployees() { return projectEmployees; }
    public void setProjectEmployees(List<Employee> ProjectEmployees) { this.projectEmployees = ProjectEmployees; }
}
