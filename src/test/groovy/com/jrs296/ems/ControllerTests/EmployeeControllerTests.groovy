package com.jrs296.ems.ControllerTests

import com.fasterxml.jackson.databind.ObjectMapper
import com.jrs296.ems.controller.EmployeeController;
import com.jrs296.ems.models.DTOs.InputDTOs.EmployeeRegisterInputDTO;
import com.jrs296.ems.models.DTOs.OutputDTOs.EmployeeOutputDTO
import com.jrs296.ems.models.entity.Employee;
import com.jrs296.ems.service.EmployeeService;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(EmployeeController.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void getAllEmployees() throws Exception {
        when(employeeService.fetchAllEmployees()).thenReturn(Collections.singletonList(new Employee()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/service/employee")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getEmployeeByID() throws Exception {
        when(employeeService.getEmployeeByID(anyInt())).thenReturn(new Employee());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/service/employee/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateEmployee() throws Exception {
        EmployeeRegisterInputDTO inputDTO = new EmployeeRegisterInputDTO();
        inputDTO.setEmployeeName("Test");

        when(employeeService.updateEmployeeById(any(EmployeeRegisterInputDTO.class))).thenReturn("Update: SUCCESS");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/service/employee/edit")
                .content(asJsonString(inputDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteEmployee() throws Exception {
        when(employeeService.deleteEmployeeByID(anyInt())).thenReturn("Employee deleted successfully");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/service/employee/{id}", 1)
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
