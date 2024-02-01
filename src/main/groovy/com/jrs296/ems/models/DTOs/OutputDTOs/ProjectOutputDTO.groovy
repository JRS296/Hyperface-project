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

    public ProjectOutputDTO() {}

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getProjectManagerID() {
        return projectManagerID;
    }

    public void setProjectManagerID(int projectManagerID) {
        this.projectManagerID = projectManagerID;
    }

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

    public List<EmployeeOutputDTO> getProjectEmployees() {
        return projectEmployees;
    }

    public void setProjectEmployees(List<EmployeeOutputDTO> projectEmployees) {
        this.projectEmployees = projectEmployees;
    }
}
