package com.jrs296.ems.service;

import com.jrs296.ems.models.entity.Project;
import com.jrs296.ems.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Objects;


@Service
public class ProjectService {
    @Autowired
    private ProjectRepository ProjectRepository;


    public Project saveProject(Project Project) {
        return ProjectRepository.save(Project);
    }

    public List<Project> fetchAllProjects() {
        return ProjectRepository.findAll();
    }

    public Project getProjectById(int id) {
        Optional<Project> Project = ProjectRepository.findById(id);
        return Project.orElse(null);
    }

    public Project updateEmployeeById(int id, Project Project) {
        Optional<Project> tempProject = ProjectRepository.findById(id);

        if (tempProject.isPresent()) {
            Project originalEmployee = tempProject.get();

            if (Objects.nonNull(Project.getProjectName()) && !"".equalsIgnoreCase(Project.getProjectName())) {
                originalEmployee.setProjectName(Project.getProjectName());
            }
            if (Objects.nonNull(Project.getProjectEmployees())) {
                originalEmployee.setProjectEmployees(Project.getProjectEmployees());
            }
//            if (Objects.nonNull(Project.getProjectDepartment())) {
//                originalEmployee.setProjectDepartment(Project.getProjectDepartment());
//            }

            return ProjectRepository.save(originalEmployee);
        }
        return null;
    }

    public String deleteProjectById(int id) {
        if (ProjectRepository.findById(id).isPresent()) {
            ProjectRepository.deleteById(id);
            return "Employee deleted successfully";
        }
        return "No such employee in the database";
    }
}
