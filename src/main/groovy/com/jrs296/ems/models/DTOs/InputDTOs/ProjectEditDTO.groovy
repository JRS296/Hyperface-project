package com.jrs296.ems.models.DTOs.InputDTOs;

import com.jrs296.ems.models.entity.Department;
import com.jrs296.ems.models.entity.Project
import groovy.transform.Canonical;
import jakarta.validation.constraints.NotNull;

@Canonical
public class ProjectEditDTO {

    @NotNull(message = "Project Name is required.")
    private String projectName;

    @NotNull(message = "Department must be specified for a given project.")
    private int projectDepartmentID;

    public Project toProject(Department department) {
        Project temp = new Project(projectName);
        temp.setProjectEmployees(new ArrayList<>(10));
        temp.setProjectDepartment(department);
        return temp;
    }

    public ProjectEditDTO() {}

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getProjectDepartmentID() {
        return projectDepartmentID;
    }

    public void setProjectDepartmentID(int projectDepartmentID) {
        this.projectDepartmentID = projectDepartmentID;
    }
}

