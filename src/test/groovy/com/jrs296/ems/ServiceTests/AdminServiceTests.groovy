package com.jrs296.ems.ServiceTests

import com.jrs296.ems.models.DTOs.InputDTOs.AssignManagerDTO;
import com.jrs296.ems.models.DTOs.InputDTOs.AssignUserDTO;
import com.jrs296.ems.models.entity.Department;
import com.jrs296.ems.models.entity.Employee;
import com.jrs296.ems.models.entity.Project;
import com.jrs296.ems.service.AdminService;
import com.jrs296.ems.service.DepartmentService;
import com.jrs296.ems.service.EmployeeService;
import com.jrs296.ems.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTests {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private DepartmentService departmentService;

    @Mock
    private ProjectService projectService;

    @Test
    void setManagerForDepartmentsAndProjects_AdminAssignsDepartmentManager_Success() {
        // Arrange
        AssignManagerDTO assignManagerDTO = new AssignManagerDTO();
        assignManagerDTO.setEmployeeID(2); // Existing employee ID
        assignManagerDTO.setAssignToRole("DEPT_MANAGER");
        assignManagerDTO.setAssignToRoleID(1); // Existing department ID

        Authentication authentication = new UsernamePasswordAuthenticationToken("admin", null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Employee employee = new Employee();
        employee.setEmployeeId(2);
        Department department = new Department();
        department.setDepartmentID(1);

        when(employeeService.getEmployeeByID(2)).thenReturn(employee);
        when(departmentService.getDepartmentById(1)).thenReturn(department);

        // Act
        String result = adminService.setManagerForDepartmentsAndProjects(assignManagerDTO);

        // Assert
        assertEquals("ROLE CHANGE SUCCESSFUL", result);
        assertEquals(2, department.getDepartmentManagerID());
        assertEquals("DEPT_MANAGER", employee.getRole());
        assertEquals(department, employee.getEmployeeDepartment());
        verify(employeeService).saveEmployee(employee);
        verify(departmentService).saveDepartment(department);
    }

    // Add more tests covering various scenarios for setManagerForDepartmentsAndProjects

    @Test
    void setEmployeeToDepartmentOrProject_AdminAssignsEmployee_Success() {
        // Arrange
        AssignUserDTO assignUserDTO = new AssignUserDTO();
        assignUserDTO.setEmployeeID(2); // Existing employee ID
        assignUserDTO.setAssignToDepartmentID(1); // Existing department ID
        assignUserDTO.setAssignToProjectID(1); // Existing project ID

        Authentication authentication = new UsernamePasswordAuthenticationToken("admin", null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Employee employee = new Employee();
        employee.setEmployeeId(2);
        Department department = new Department();
        department.setDepartmentID(1);
        Project project = new Project();
        project.setProjectID(1);

        when(employeeService.getEmployeeByID(2)).thenReturn(employee);
        when(departmentService.getDepartmentById(1)).thenReturn(department);
        when(projectService.getProjectById(1)).thenReturn(project);

        // Act
        String result = adminService.setEmployeeToDepartmentOrProject(assignUserDTO);

        // Assert
        assertEquals("EMPLOYEE ASSIGNMENT SUCCESSFUL", result);
        assertEquals(department, employee.getEmployeeDepartment());
        assertEquals(project, employee.getEmployeeProject());
        assertTrue(project.getProjectEmployees().contains(employee));
        verify(employeeService).saveEmployee(employee);
        verify(departmentService).saveDepartment(department);
        verify(projectService).saveProject(project);
    }

    // Add more tests covering various scenarios for setEmployeeToDepartmentOrProject
}
