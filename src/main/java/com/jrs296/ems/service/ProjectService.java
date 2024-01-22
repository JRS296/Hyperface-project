package com.jrs296.ems.service;

import com.jrs296.ems.entity.Department;
import com.jrs296.ems.entity.Project;
import com.jrs296.ems.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project displayData(Project project) {
        return project;
    }
}
