package com.jrs296.ems.models.DTOs.InputDTOs;

import com.jrs296.ems.models.entity.Department;
import com.jrs296.ems.models.entity.Project;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;

@Data
public class ProjectInputDTO {

    @NotNull(message = "Project Name is required.")
    private String projectName;

    @NotNull(message = "Department must be specified for a given project.")
    private int projectDepartmentID;

    public Project toProject(Department department) {
        Project temp = new Project(projectName);
        temp.setProjectEmployees(new ArrayList<>(10));
        temp.setProjectDepartment(department);
        temp.setProjectManagerID(-1);
        return temp;
    }
}
