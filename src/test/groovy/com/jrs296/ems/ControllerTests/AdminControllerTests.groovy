package com.jrs296.ems.ControllerTests

import com.jrs296.ems.controller.AdminController
import com.jrs296.ems.models.DTOs.InputDTOs.AssignManagerDTO;
import com.jrs296.ems.models.DTOs.InputDTOs.AssignUserDTO
import com.jrs296.ems.service.AdminService
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminControllerTests {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @Test // TODO Rewrite Tests for more specific case scenarios
    void assignManagerRole_ValidAssignManagerDTO_CallsAdminService() {
        // Arrange
        AssignManagerDTO assignManagerDTO = new AssignManagerDTO();

        // Act
        adminController.assignManagerRole(assignManagerDTO);

        // Assert
        verify(adminService, times(1)).setManagerForDepartmentsAndProjects(assignManagerDTO);
    }

    @Test // TODO Rewrite Tests for more specific case scenarios
    void assignEmployeeRole_ValidAssignUserDTO_CallsAdminService() {
        // Arrange
        AssignUserDTO assignUserDTO = new AssignUserDTO();

        // Act
        adminController.assignEmployeeRole(assignUserDTO);

        // Assert
        verify(adminService, times(1)).setEmployeeToDepartmentOrProject(assignUserDTO);
    }
}
