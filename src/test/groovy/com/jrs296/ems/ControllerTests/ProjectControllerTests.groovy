package com.jrs296.ems.ControllerTests

import com.fasterxml.jackson.databind.ObjectMapper
import com.jrs296.ems.controller.ProjectController
import com.jrs296.ems.filters.JwtAuthFilter;
import com.jrs296.ems.models.DTOs.InputDTOs.ProjectInputDTO;
import com.jrs296.ems.models.DTOs.OutputDTOs.ProjectOutputDTO;
import com.jrs296.ems.models.DTOs.OutputDTOs.ResponseObject
import com.jrs296.ems.models.entity.Project
import com.jrs296.ems.service.DepartmentService;
import com.jrs296.ems.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(ProjectController.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class ProjectControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private DepartmentService departmentService

    @MockBean
    private JwtAuthFilter jwtAuthFilter

    @Test
    void getAllProjects() throws Exception {
        when(projectService.fetchAllProjects()).thenReturn(Collections.singletonList(new Project()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/service/project")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getProjectById() throws Exception {
        when(projectService.getProjectById(anyInt())).thenReturn(new Project());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/service/project/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createNewProject() throws Exception {
        ProjectInputDTO inputDTO = new ProjectInputDTO();
        inputDTO.setProjectName("Test");

        when(projectService.saveProjectInputDTO(any(ProjectInputDTO.class))).thenReturn(new Project());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/service/project")
                .content(asJsonString(inputDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateProject() throws Exception {
        ProjectInputDTO inputDTO = new ProjectInputDTO();
        inputDTO.setProjectName("Test");

        when(projectService.updateProjectById(anyInt(), any(ProjectInputDTO.class))).thenReturn(new Project());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/service/project/{id}", 1)
                .content(asJsonString(inputDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteProject() throws Exception {
        when(projectService.deleteProjectById(anyInt())).thenReturn("Project deleted successfully");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/service/project/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
