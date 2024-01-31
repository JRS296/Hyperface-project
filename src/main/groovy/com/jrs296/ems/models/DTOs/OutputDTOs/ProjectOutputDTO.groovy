package com.jrs296.ems.models.DTOs.OutputDTOs;

import com.jrs296.ems.models.entity.Project
import groovy.transform.Canonical;

@Canonical
public class ProjectOutputDTO {

    private int projectID;

    private int projectManagerID;

    private String projectName;

    private int projectDepartmentID;

    private List<EmployeeOutputDTO> projectEmployees;

    public ProjectOutputDTO(int projectID, int projectManagerID, String projectName, List<EmployeeOutputDTO> projectEmployees, int projectDepartmentID) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.projectEmployees = projectEmployees;
        this.projectDepartmentID = projectDepartmentID;
        this.projectManagerID = projectManagerID;
    }

    public static ProjectOutputDTO toProjectOutputDTO(Project project) {
        return new ProjectOutputDTO(project.getProjectID(), project.getProjectManagerID(), project.getProjectName(), EmployeeOutputDTO.toListEmployeesOutputDTO(project.getProjectEmployees()), project.getProjectDepartment().getDepartmentID());
    }

    public static List<ProjectOutputDTO> toListProjectOutputDTO(List<Project> projects) {
        List<ProjectOutputDTO> outputDTOS = new ArrayList<ProjectOutputDTO>(projects.size());
        for (Project project : projects) {
            outputDTOS.add(toProjectOutputDTO(project));
        }
        return outputDTOS;
    }
}
