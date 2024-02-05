package com.jrs296.ems.ServiceTests

import com.jrs296.ems.models.DTOs.InputDTOs.EmployeeRegisterInputDTO;
import com.jrs296.ems.models.entity.Employee;
import com.jrs296.ems.repository.EmployeeRepository
import com.jrs296.ems.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTests {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void saveEmployee_ValidEmployee_ReturnsSavedEmployee() {
        // Arrange
        Employee employee = new Employee();
        when(employeeRepository.save(employee)).thenReturn(employee);

        // Act
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // Assert
        assertNotNull(savedEmployee);
        assertEquals(employee, savedEmployee);
    }

    @Test
    void fetchAllEmployees_NoEmployees_ReturnsEmptyList() {
        // Arrange
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        Iterable<Employee> employees = employeeService.fetchAllEmployees();

        // Assert
        assertNotNull(employees);
        assertFalse(employees.iterator().hasNext());
    }

    // Add more test cases for fetchAllEmployees to cover scenarios with employees

    @Test
    void getEmployeeByID_ExistingId_ReturnsEmployee() {
        // Arrange
        int employeeId = 1;
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        // Act
        Employee foundEmployee = employeeService.getEmployeeByID(employeeId);

        // Assert
        assertNotNull(foundEmployee);
        assertEquals(employeeId, foundEmployee.getEmployeeId());
    }

    // Add more test cases for getEmployeeByID to cover scenarios with non-existing employee ID

    @Test
    void getEmployeeByUsername_ExistingUsername_ReturnsEmployee() {
        // Arrange
        String username = "testUser";
        Employee employee = new Employee();
        when(employeeRepository.findByUsername(username)).thenReturn(Optional.of(employee));

        // Act
        Employee foundEmployee = employeeService.getEmployeeByUsername(username);

        // Assert
        assertNotNull(foundEmployee);
        assertEquals(username, foundEmployee.getUsername());
    }

    // Add more test cases for getEmployeeByUsername to cover scenarios with non-existing username

    @Test
    void updateEmployeeById_ValidInput_ReturnsSuccessMessage() {
        // Arrange
        int employeeId = 1;
        EmployeeRegisterInputDTO inputDTO = new EmployeeRegisterInputDTO();
        inputDTO.setEmployeeEmail("test@example.com");
        inputDTO.setPassword("password");
        inputDTO.setEmployeeName("Test Employee");
        inputDTO.setUserName("testUser");

        Employee employee = new Employee();
        when(employeeRepository.findByUsername(anyString())).thenReturn(Optional.of(employee));
        when(passwordEncoder.encode(inputDTO.getPassword())).thenReturn("encodedPassword");

        // Mock SecurityContext
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = new User(inputDTO.getUserName(), "password", Collections.emptyList());
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Act
        String result = employeeService.updateEmployeeById(inputDTO);

        // Assert
        assertEquals("Update: SUCCESS", result);
        verify(employeeRepository, times(1)).save(employee);
        assertEquals(inputDTO.getEmployeeEmail(), employee.getEmployeeEmail());
        assertEquals("encodedPassword", employee.getEmployeePassword());
        assertEquals(inputDTO.getEmployeeName(), employee.getEmployeeName());
        assertEquals(inputDTO.getUserName(), employee.getUsername());
    }

    // Add more test cases for updateEmployeeById to cover scenarios with invalid input

    @Test
    void deleteEmployeeByID_ExistingId_ReturnsSuccessMessage() {
        // Arrange
        int employeeId = 1;
        Employee employee = new Employee();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        // Act
        String result = employeeService.deleteEmployeeByID(employeeId);

        // Assert
        assertEquals("Employee deleted successfully", result);
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

    // Add more test cases for deleteEmployeeByID to cover scenarios with non-existing employee ID
}
