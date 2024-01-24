package com.jrs296.ems.models.DTOs.OutputDTOs;

import com.jrs296.ems.models.entity.Employee;
import com.jrs296.ems.models.entity.Project;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectOutputDTO {

    private int projectID;

    private String projectName;

    private int projectDepartmentID;

    private List<Employee> projectEmployees;

    public ProjectOutputDTO(int projectID, String projectName, List<Employee> projectEmployees, int projectDepartmentID) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.projectEmployees = projectEmployees;
        this.projectDepartmentID = projectDepartmentID;
    }

    public static ProjectOutputDTO toProjectOutputDTO(Project project) {
        return new ProjectOutputDTO(project.getProjectID(), project.getProjectName(), project.getProjectEmployees(), project.getProjectDepartment().getDepartmentID());
    }

    public static List<ProjectOutputDTO> toListProjectOutputDTO(List<Project> projects) {
        List<ProjectOutputDTO> outputDTOS = new ArrayList<ProjectOutputDTO>(projects.size());
        for (Project project : projects) {
            outputDTOS.add(toProjectOutputDTO(project));
        }
        return outputDTOS;
    }
}
