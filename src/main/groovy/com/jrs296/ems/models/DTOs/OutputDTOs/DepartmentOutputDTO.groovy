package com.jrs296.ems.models.DTOs.OutputDTOs;



import com.jrs296.ems.models.entity.Department
import groovy.transform.Canonical;

@Canonical
public class DepartmentOutputDTO {
    private int departmentID;

    private int departmentManagerID;

    private String departmentName;

    private List<ProjectOutputDTO> departmentProjects;

    private List<EmployeeOutputDTO> departmentEmployeesUnAssignedToProjects;

    public DepartmentOutputDTO(int departmentID, int departmentManagerID, String departmentName, List<ProjectOutputDTO> departmentProjects, List<EmployeeOutputDTO> departmentEmployeesUnAssignedToProjects) {
        this.departmentID = departmentID;
        this.departmentManagerID = departmentManagerID;
        this.departmentName = departmentName;
        this.departmentProjects = departmentProjects;
        this.departmentEmployeesUnAssignedToProjects = departmentEmployeesUnAssignedToProjects;
    }

    public static DepartmentOutputDTO toDepartmentOutputDTO(Department department) {
        List<EmployeeOutputDTO> filteredEmployees = EmployeeOutputDTO.toListEmployeesOutputDTO(department.getAllEmployees())
        .stream()
                .filter(employee -> employee.getEmployeeProjectID() == -1) //Return all employees that are unassigned to projects
                .toList();

        return new DepartmentOutputDTO(department.getDepartmentID(), department.getDepartmentManagerID(), department.getDepartmentName(), ProjectOutputDTO.toListProjectOutputDTO(department.getDepartmentProjects()), filteredEmployees);
    }

    public static List<DepartmentOutputDTO> toListDepartmentOutputDTO(List<Department> departments) {
        List<DepartmentOutputDTO> outputDTOS = new ArrayList<DepartmentOutputDTO>(departments.size());
        for (Department department : departments) {
            outputDTOS.add(toDepartmentOutputDTO(department));
        }
        return outputDTOS;
    }

    public DepartmentOutputDTO() {}

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public int getDepartmentManagerID() {
        return departmentManagerID;
    }

    public void setDepartmentManagerID(int departmentManagerID) {
        this.departmentManagerID = departmentManagerID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<ProjectOutputDTO> getDepartmentProjects() {
        return departmentProjects;
    }

    public void setDepartmentProjects(List<ProjectOutputDTO> departmentProjects) {
        this.departmentProjects = departmentProjects;
    }

    public List<EmployeeOutputDTO> getDepartmentEmployeesUnAssignedToProjects() {
        return departmentEmployeesUnAssignedToProjects;
    }

    public void setDepartmentEmployeesUnAssignedToProjects(List<EmployeeOutputDTO> departmentEmployeesUnAssignedToProjects) {
        this.departmentEmployeesUnAssignedToProjects = departmentEmployeesUnAssignedToProjects;
    }
}
