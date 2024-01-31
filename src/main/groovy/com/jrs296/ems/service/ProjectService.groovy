package com.jrs296.ems.service;

import com.jrs296.ems.models.DTOs.InputDTOs.ProjectInputDTO;
import com.jrs296.ems.models.entity.Department;
import com.jrs296.ems.models.entity.Employee;
import com.jrs296.ems.models.entity.Project;
import com.jrs296.ems.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;


    public Project saveProjectInputDTO(ProjectInputDTO projectInputDTO) {
        Department projectDepartment = departmentService.getDepartmentById(projectInputDTO.getProjectDepartmentID());
        if(projectDepartment==null) throw new RuntimeException("Department does not exist");
        Project project = saveProject(projectInputDTO.toProject(projectDepartment));
        projectDepartment.getDepartmentProjects().add(project);
        departmentService.saveDepartment(projectDepartment);
        return project;
    }

    public Project saveProject(Project project) { return projectRepository.save(project); }

    public List<Project> fetchAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(int id) {
        Optional<Project> Project = projectRepository.findById(id);
        return Project.orElse(null);
    }

    public Project updateProjectById(int id, ProjectInputDTO projectInputDTO) {
        Department newDepartment = departmentService.getDepartmentById(projectInputDTO.getProjectDepartmentID());
        if(newDepartment==null) throw new RuntimeException("Department does not exist");
        if(newDepartment.getDepartmentManagerID() == -1) throw new RuntimeException("Department does not have a manager, first assign a manager");
        Optional<Project> tempProject = projectRepository.findById(id);

        if (tempProject.isPresent()) {
            Project project = tempProject.get();
            project.setProjectName(projectInputDTO.getProjectName());
            project.setProjectDepartment(newDepartment);
            Department oldDepartment = project.getProjectDepartment();
            project =  projectRepository.save(project);

            // Change old and new dept
            oldDepartment.getDepartmentProjects().remove(project);
            newDepartment.getDepartmentProjects().add(project);

            departmentService.saveDepartment(newDepartment);
            departmentService.saveDepartment(oldDepartment);
            return project;
        } else {
            throw new RuntimeException("Project Not Found");
        }
    }

    public String deleteProjectById(int id) {
        if (projectRepository.findById(id).isPresent()) {
            // Decouple all Employees Associated with this Department
            Project project = projectRepository.findById(id).get();
            Department projectDepartment = project.getProjectDepartment();

            projectDepartment.getDepartmentProjects().remove(project);
            departmentService.saveDepartment(projectDepartment);

            project.setProjectDepartment(null);
            List<Employee> employees = project.getProjectEmployees();
            project.getProjectEmployees().clear();
            saveProject(project);
            projectRepository.deleteById(id);

            for (Employee employee : employees) {
                employee.setEmployeeProject(null);
                employee.setRole("USER");
                employeeService.saveEmployee(employee);
            }

            return "Project deleted successfully (Employees Persisted)";
        } else throw new RuntimeException("No such Project in the database");
    }
}
