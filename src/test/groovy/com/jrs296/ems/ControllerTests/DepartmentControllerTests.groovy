package com.jrs296.ems.ControllerTests

import com.fasterxml.jackson.databind.ObjectMapper
import com.jrs296.ems.controller.DepartmentController;
import com.jrs296.ems.models.DTOs.InputDTOs.DepartmentInputDTO;
import com.jrs296.ems.models.DTOs.OutputDTOs.DepartmentOutputDTO;
import com.jrs296.ems.models.DTOs.OutputDTOs.ResponseObject;
import com.jrs296.ems.models.entity.Department;
import com.jrs296.ems.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(DepartmentController.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class DepartmentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Test
    void getAllDepartments() throws Exception {
        when(departmentService.fetchAllDepartments()).thenReturn(Collections.singletonList(new Department()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/service/department")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getDepartmentById() throws Exception {
        when(departmentService.getDepartmentById(anyInt())).thenReturn(new Department());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/service/department/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void saveDepartment() throws Exception {
        DepartmentInputDTO inputDTO = new DepartmentInputDTO();
        inputDTO.setDepartmentName("Test");

        when(departmentService.saveDepartment(any(DepartmentInputDTO.class))).thenReturn(new Department());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/service/department")
                .content(asJsonString(inputDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateDepartment() throws Exception {
        DepartmentInputDTO inputDTO = new DepartmentInputDTO();
        inputDTO.setDepartmentName("Test");

        when(departmentService.updateDepartmentByID(anyInt(), any(DepartmentInputDTO.class))).thenReturn(new Department());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/service/department/{id}", 1)
                .content(asJsonString(inputDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteDepartment() throws Exception {
        when(departmentService.deleteDepartmentById(anyInt())).thenReturn("Department Deleted Successfully.");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/service/department/{id}", 1)
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
