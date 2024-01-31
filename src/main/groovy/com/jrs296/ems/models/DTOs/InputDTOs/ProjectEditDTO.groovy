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
}

