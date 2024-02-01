package com.jrs296.ems.ControllerTests

import com.jrs296.ems.controller.AdminController
import com.jrs296.ems.models.DTOs.InputDTOs.AssignManagerDTO;
import com.jrs296.ems.models.DTOs.InputDTOs.AssignUserDTO;
import com.jrs296.ems.service.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminControllerTests {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @Test
    void testForAuth_AuthenticatedUser_ReturnsSuccess() {
        // Arrange
        UserDetails userDetails = new User("testUser", "password", null);
        Authentication authentication = new TestingAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Act
        String result = adminController.testForAuth();

        // Assert
        assertEquals("Success", result);
    }

    @Test
    void assignManagerRole_ValidAssignManagerDTO_CallsAdminService() {
        // Arrange
        AssignManagerDTO assignManagerDTO = new AssignManagerDTO();

        // Act
        adminController.assignManagerRole(assignManagerDTO);

        // Assert
        verify(adminService, times(1)).setManagerForDepartmentsAndProjects(assignManagerDTO);
    }

    @Test
    void assignEmployeeRole_ValidAssignUserDTO_CallsAdminService() {
        // Arrange
        AssignUserDTO assignUserDTO = new AssignUserDTO();

        // Act
        adminController.assignEmployeeRole(assignUserDTO);

        // Assert
        verify(adminService, times(1)).setEmployeeToDepartmentOrProject(assignUserDTO);
    }
}
