package com.jrs296.ems.ServiceTests

import com.jrs296.ems.models.DTOs.InputDTOs.DepartmentInputDTO;
import com.jrs296.ems.models.entity.Department;
import com.jrs296.ems.models.entity.Employee;
import com.jrs296.ems.repository.DepartmentRepository
import com.jrs296.ems.service.DepartmentService
import com.jrs296.ems.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTests {

    @InjectMocks
    private DepartmentService departmentService;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeService employeeService;

    @Test
    void saveDepartment_ValidDepartment_ReturnsSavedDepartment() {
        // Arrange
        Department department = new Department();
        department.setDepartmentName("Test Department");

        when(departmentRepository.save(department)).thenReturn(department);

        // Act
        Department savedDepartment = departmentService.saveDepartment(department);

        // Assert
        assertNotNull(savedDepartment);
        assertEquals(department, savedDepartment);
    }

    @Test
    void fetchAllDepartments_NoDepartments_ReturnsEmptyList() {
        // Arrange
        when(departmentRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Department> departments = departmentService.fetchAllDepartments();

        // Assert
        assertNotNull(departments);
        assertTrue(departments.isEmpty());
    }

    // Add more test cases for fetchAllDepartments to cover scenarios with departments

    @Test
    void getDepartmentById_ExistingId_ReturnsDepartment() {
        // Arrange
        int departmentId = 1;
        Department department = new Department();
        department.setDepartmentID(departmentId);
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));

        // Act
        Department foundDepartment = departmentService.getDepartmentById(departmentId);

        // Assert
        assertNotNull(foundDepartment);
        assertEquals(departmentId, foundDepartment.getDepartmentID());
    }

    // Add more test cases for getDepartmentById to cover scenarios with non-existing department ID

    @Test
    void updateDepartmentByID_ExistingId_ReturnsUpdatedDepartment() {
        // Arrange
        int departmentId = 1;
        DepartmentInputDTO departmentInputDTO = new DepartmentInputDTO();
        departmentInputDTO.setDepartmentName("Updated Department");

        Department originalDepartment = new Department();
        originalDepartment.setDepartmentID(departmentId);
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(originalDepartment));

        // Act
        Department updatedDepartment = departmentService.updateDepartmentByID(departmentId, departmentInputDTO);

        // Assert
        assertNotNull(updatedDepartment);
        assertEquals(departmentInputDTO.getDepartmentName(), updatedDepartment.getDepartmentName());
    }

    // Add more test cases for updateDepartmentByID to cover scenarios with non-existing department ID

    @Test
    void deleteDepartmentById_ExistingId_ReturnsSuccessMessage() {
        // Arrange
        int departmentId = 1;
        Department department = new Department();
        department.setDepartmentID(departmentId);
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));

        // Act
        String result = departmentService.deleteDepartmentById(departmentId);

        // Assert
        assertEquals("Department deleted successfully (Employees Persisted)", result);
        verify(employeeService, times(1)).saveEmployee(any(Employee.class));
        verify(departmentRepository, times(1)).deleteById(departmentId);
    }

    // Add more test cases for deleteDepartmentById to cover scenarios with non-existing department ID
}
