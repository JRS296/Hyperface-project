package com.jrs296.ems.models.DTOs.OutputDTOs;



import com.jrs296.ems.models.entity.Department;
import com.jrs296.ems.models.entity.Employee;
import com.jrs296.ems.models.entity.Project;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DepartmentOutputDTO {
    private int departmentID;

    private String departmentName;

    private List<ProjectOutputDTO> departmentProjects;

    private List<Employee> departmentEmployeesUnAssignedToProjects; //Replace with EmployeeDTO

    public DepartmentOutputDTO(int departmentID, String departmentName, List<ProjectOutputDTO> departmentProjects, List<Employee> departmentEmployeesUnAssignedToProjects) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.departmentProjects = departmentProjects;
        this.departmentEmployeesUnAssignedToProjects = departmentEmployeesUnAssignedToProjects;
    }

    public static DepartmentOutputDTO toDepartmentOutputDTO(Department department) {
        return new DepartmentOutputDTO(department.getDepartmentID(), department.getDepartmentName(), ProjectOutputDTO.toListProjectOutputDTO(department.getDepartmentProjects()), department.getDepartmentEmployeesUnAssignedToProjects());
    }

    public static List<DepartmentOutputDTO> toListProjectOutputDTO(List<Department> departments) {
        List<DepartmentOutputDTO> outputDTOS = new ArrayList<DepartmentOutputDTO>(departments.size());
        for (Department department : departments) {
            outputDTOS.add(toDepartmentOutputDTO(department));
        }
        return outputDTOS;
    }
}
