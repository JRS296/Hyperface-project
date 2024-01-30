package com.jrs296.ems.models.DTOs.OutputDTOs;



import com.jrs296.ems.models.entity.Department;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
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
        return new DepartmentOutputDTO(department.getDepartmentID(), department.getDepartmentManagerID(), department.getDepartmentName(), ProjectOutputDTO.toListProjectOutputDTO(department.getDepartmentProjects()), EmployeeOutputDTO.toListEmployeesOutputDTO(department.getUnAssignedToProject()));
    }

    public static List<DepartmentOutputDTO> toListProjectOutputDTO(List<Department> departments) {
        List<DepartmentOutputDTO> outputDTOS = new ArrayList<DepartmentOutputDTO>(departments.size());
        for (Department department : departments) {
            outputDTOS.add(toDepartmentOutputDTO(department));
        }
        return outputDTOS;
    }
}
