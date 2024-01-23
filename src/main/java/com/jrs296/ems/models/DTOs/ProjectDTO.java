package com.jrs296.ems.models.DTOs;

import com.jrs296.ems.models.entity.Department;
import com.jrs296.ems.models.entity.Project;
import com.jrs296.ems.repository.DepartmentRepository;
import com.jrs296.ems.service.DepartmentService;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@Data
public class ProjectDTO {

    @NotNull(message = "Project Name is required.")
    private String projectName;

    @NotNull(message = "Department must be specified for a given project.")
    private int projectDepartmentID;

    public Project toProject(Department department) {
        Project temp = new Project(projectName);
        temp.setProjectEmployees(new ArrayList<>(10));
        temp.setProjectDepartment(department);
        temp.setProjectDepartmentID(department.getDepartmentID());
        return temp;
    }
}
