package com.jrs296.ems.MiscellaneousTests

import com.jrs296.ems.models.DTOs.InputDTOs.AssignManagerDTO
import com.jrs296.ems.models.DTOs.InputDTOs.AssignUserDTO
import com.jrs296.ems.models.DTOs.InputDTOs.DepartmentInputDTO
import com.jrs296.ems.models.DTOs.InputDTOs.EmployeeLoginInputDTO
import com.jrs296.ems.models.DTOs.InputDTOs.EmployeeRegisterInputDTO
import com.jrs296.ems.models.DTOs.InputDTOs.ProjectEditDTO
import com.jrs296.ems.models.DTOs.InputDTOs.ProjectInputDTO
import com.jrs296.ems.models.DTOs.OutputDTOs.DepartmentOutputDTO
import com.jrs296.ems.models.DTOs.OutputDTOs.EmployeeOutputDTO
import com.jrs296.ems.models.DTOs.OutputDTOs.ProjectOutputDTO
import com.jrs296.ems.models.DTOs.OutputDTOs.ResponseObject
import com.jrs296.ems.models.entity.Department
import com.jrs296.ems.models.entity.Employee
import com.jrs296.ems.models.entity.Project
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import jakarta.validation.*;

class DepartmentTest {

    private Department department;

    @BeforeEach
    public void setUp() {
        department = new Department();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(department);
        assertNotNull(department.getDepartmentProjects());
        assertNotNull(department.getAllEmployees());
        assertEquals(0, department.getDepartmentProjects().size());
        assertEquals(0, department.getAllEmployees().size());
    }

    @Test
    public void testConstructorWithName() {
        String departmentName = "Test Department";
        department = new Department(departmentName);
        assertEquals(departmentName, department.getDepartmentName());
    }

    @Test
    public void testConstructorWithNameAndManagerID() {
        String departmentName = "Test Department";
        int managerID = 1;
        department = new Department(departmentName, managerID);
        assertEquals(departmentName, department.getDepartmentName());
        assertEquals(managerID, department.getDepartmentManagerID());
    }

    @Test
    public void testGettersAndSetters() {
        int departmentID = 1;
        String departmentName = "Test Department";
        int managerID = 1;

        department.setDepartmentID(departmentID);
        department.setDepartmentName(departmentName);
        department.setDepartmentManagerID(managerID);

        assertEquals(departmentID, department.getDepartmentID());
        assertEquals(departmentName, department.getDepartmentName());
        assertEquals(managerID, department.getDepartmentManagerID());
    }

    @Test
    public void testDepartmentProjects() {
        Project project1 = new Project();
        Project project2 = new Project();

        List<Project> projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);

        department.setDepartmentProjects(projects);

        assertEquals(projects, department.getDepartmentProjects());
    }

    @Test
    public void testAllEmployees() {
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();

        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);

        department.setAllEmployees(employees);

        assertEquals(employees, department.getAllEmployees());
    }
}

public class EmployeeTest {

    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = new Employee();
    }

    @Test
    public void testParameterizedConstructor() {
        String employeeName = "John Doe";
        String username = "johndoe";
        String employeeEmail = "john@example.com";
        String employeePassword = "password";

        employee = new Employee(employeeName, username, employeeEmail, employeePassword);

        assertEquals(employeeName, employee.getEmployeeName());
        assertEquals(username, employee.getUsername());
        assertEquals(employeeEmail, employee.getEmployeeEmail());
        assertEquals(employeePassword, employee.getEmployeePassword());
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(employee);
    }

    @Test
    public void testGettersAndSetters() {
        int employeeId = 1;
        String employeeName = "John Doe";
        String username = "johndoe";
        String employeeEmail = "john@example.com";
        String employeePassword = "password";
        float employeeSalary = 50000;
        Project project = new Project();
        Department department = new Department();
        String role = "ADMIN";

        employee.setEmployeeId(employeeId);
        employee.setEmployeeName(employeeName);
        employee.setUsername(username);
        employee.setEmployeeEmail(employeeEmail);
        employee.setEmployeePassword(employeePassword);
        employee.setEmployeeSalary(employeeSalary);
        employee.setEmployeeProject(project);
        employee.setEmployeeDepartment(department);
        employee.setRole(role);

        assertEquals(employeeId, employee.getEmployeeId());
        assertEquals(employeeName, employee.getEmployeeName());
        assertEquals(username, employee.getUsername());
        assertEquals(employeeEmail, employee.getEmployeeEmail());
        assertEquals(employeePassword, employee.getEmployeePassword());
        assertEquals(employeeSalary, employee.getEmployeeSalary());
        assertEquals(project, employee.getEmployeeProject());
        assertEquals(department, employee.getEmployeeDepartment());
        assertEquals(role, employee.getRole());
    }

    @Test
    public void testEmployeeProject() {
        Project project = new Project();
        employee.setEmployeeProject(project);
        assertEquals(project, employee.getEmployeeProject());
    }

    @Test
    public void testEmployeeDepartment() {
        Department department = new Department();
        employee.setEmployeeDepartment(department);
        assertEquals(department, employee.getEmployeeDepartment());
    }
}

public class ProjectTest {

    private Project project;

    @BeforeEach
    public void setUp() {
        project = new Project();
    }

    @Test
    public void testParameterizedConstructor() {
        String projectName = "Project A";
        int projectManagerID = 1;

        project = new Project(projectName, projectManagerID);

        assertEquals(projectName, project.getProjectName());
        assertEquals(projectManagerID, project.getProjectManagerID());
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(project);
    }

    @Test
    public void testGettersAndSetters() {
        int projectID = 1;
        String projectName = "Project A";
        int projectManagerID = 1;
        Department department = new Department();
        List<Employee> employees = new ArrayList<>();

        project.setProjectID(projectID);
        project.setProjectName(projectName);
        project.setProjectManagerID(projectManagerID);
        project.setProjectDepartment(department);
        project.setProjectEmployees(employees);

        assertEquals(projectID, project.getProjectID());
        assertEquals(projectName, project.getProjectName());
        assertEquals(projectManagerID, project.getProjectManagerID());
        assertEquals(department, project.getProjectDepartment());
        assertEquals(employees, project.getProjectEmployees());
    }

    @Test
    public void testProjectDepartment() {
        Department department = new Department();
        project.setProjectDepartment(department);
        assertEquals(department, project.getProjectDepartment());
    }

    @Test
    public void testProjectEmployees() {
        List<Employee> employees = new ArrayList<>();
        project.setProjectEmployees(employees);
        assertEquals(employees, project.getProjectEmployees());
    }
}

public class DepartmentOutputDTOTest {

    private DepartmentOutputDTO departmentOutputDTO;

    @BeforeEach
    public void setUp() {
        departmentOutputDTO = new DepartmentOutputDTO();
    }

    @Test
    public void testParameterizedConstructor() {
        int departmentID = 1;
        int departmentManagerID = 1;
        String departmentName = "Department A";
        List<ProjectOutputDTO> departmentProjects = new ArrayList<>();
        List<EmployeeOutputDTO> departmentEmployees = new ArrayList<>();

        departmentOutputDTO = new DepartmentOutputDTO(departmentID, departmentManagerID, departmentName, departmentProjects, departmentEmployees);

        assertEquals(departmentID, departmentOutputDTO.getDepartmentID());
        assertEquals(departmentManagerID, departmentOutputDTO.getDepartmentManagerID());
        assertEquals(departmentName, departmentOutputDTO.getDepartmentName());
        assertEquals(departmentProjects, departmentOutputDTO.getDepartmentProjects());
        assertEquals(departmentEmployees, departmentOutputDTO.getDepartmentEmployeesUnAssignedToProjects());
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(departmentOutputDTO);
    }

    @Test
    public void testGettersAndSetters() {
        int departmentID = 1;
        int departmentManagerID = 1;
        String departmentName = "Department A";
        List<ProjectOutputDTO> departmentProjects = new ArrayList<>();
        List<EmployeeOutputDTO> departmentEmployees = new ArrayList<>();

        departmentOutputDTO.setDepartmentID(departmentID);
        departmentOutputDTO.setDepartmentManagerID(departmentManagerID);
        departmentOutputDTO.setDepartmentName(departmentName);
        departmentOutputDTO.setDepartmentProjects(departmentProjects);
        departmentOutputDTO.setDepartmentEmployeesUnAssignedToProjects(departmentEmployees);

        assertEquals(departmentID, departmentOutputDTO.getDepartmentID());
        assertEquals(departmentManagerID, departmentOutputDTO.getDepartmentManagerID());
        assertEquals(departmentName, departmentOutputDTO.getDepartmentName());
        assertEquals(departmentProjects, departmentOutputDTO.getDepartmentProjects());
        assertEquals(departmentEmployees, departmentOutputDTO.getDepartmentEmployeesUnAssignedToProjects());
    }

    @Test
    public void testToDepartmentOutputDTO() {
        // Create a Department
        Department department = new Department();
        department.setDepartmentID(1);
        department.setDepartmentManagerID(1);
        department.setDepartmentName("Department A");

        // Convert Department to DepartmentOutputDTO
        DepartmentOutputDTO outputDTO = DepartmentOutputDTO.toDepartmentOutputDTO(department);

        // Verify the conversion
        assertEquals(department.getDepartmentID(), outputDTO.getDepartmentID());
        assertEquals(department.getDepartmentManagerID(), outputDTO.getDepartmentManagerID());
        assertEquals(department.getDepartmentName(), outputDTO.getDepartmentName());
    }

    @Test
    public void testToListDepartmentOutputDTO() {
        // Create a list of Departments
        List<Department> departments = new ArrayList<>();
        Department department1 = new Department();
        department1.setDepartmentID(1);
        department1.setDepartmentManagerID(1);
        department1.setDepartmentName("Department A");
        departments.add(department1);

        Department department2 = new Department();
        department2.setDepartmentID(2);
        department2.setDepartmentManagerID(2);
        department2.setDepartmentName("Department B");
        departments.add(department2);

        // Convert list of Departments to list of DepartmentOutputDTOs
        List<DepartmentOutputDTO> outputDTOs = DepartmentOutputDTO.toListDepartmentOutputDTO(departments);

        // Verify the conversion
        assertEquals(departments.size(), outputDTOs.size());
        assertEquals(departments.get(0).getDepartmentID(), outputDTOs.get(0).getDepartmentID());
        assertEquals(departments.get(1).getDepartmentID(), outputDTOs.get(1).getDepartmentID());
    }
}

public class EmployeeOutputDTOTest {

    private EmployeeOutputDTO employeeOutputDTO;

    @BeforeEach
    public void setUp() {
        employeeOutputDTO = new EmployeeOutputDTO();
    }

    @Test
    public void testParameterizedConstructor() {
        int employeeId = 1;
        String employeeName = "John Doe";
        String userName = "johndoe";
        float employeeSalary = 50000.0f;
        int employeeProjectID = 1;
        int employeeDepartmentID = 2;
        String role = "USER";

        employeeOutputDTO = new EmployeeOutputDTO(employeeId, employeeName, userName, employeeSalary, employeeProjectID, employeeDepartmentID, role);

        assertEquals(employeeId, employeeOutputDTO.getEmployeeId());
        assertEquals(employeeName, employeeOutputDTO.getEmployeeName());
        assertEquals(userName, employeeOutputDTO.getUserName());
        assertEquals(employeeSalary, employeeOutputDTO.getEmployeeSalary());
        assertEquals(employeeProjectID, employeeOutputDTO.getEmployeeProjectID());
        assertEquals(employeeDepartmentID, employeeOutputDTO.getEmployeeDepartmentID());
        assertEquals(role, employeeOutputDTO.getRole());
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(employeeOutputDTO);
    }

    @Test
    public void testGettersAndSetters() {
        int employeeId = 1;
        String employeeName = "John Doe";
        String userName = "johndoe";
        float employeeSalary = 50000.0f;
        int employeeProjectID = 1;
        int employeeDepartmentID = 2;
        String role = "USER";

        employeeOutputDTO.setEmployeeId(employeeId);
        employeeOutputDTO.setEmployeeName(employeeName);
        employeeOutputDTO.setUserName(userName);
        employeeOutputDTO.setEmployeeSalary(employeeSalary);
        employeeOutputDTO.setEmployeeProjectID(employeeProjectID);
        employeeOutputDTO.setEmployeeDepartmentID(employeeDepartmentID);
        employeeOutputDTO.setRole(role);

        assertEquals(employeeId, employeeOutputDTO.getEmployeeId());
        assertEquals(employeeName, employeeOutputDTO.getEmployeeName());
        assertEquals(userName, employeeOutputDTO.getUserName());
        assertEquals(employeeSalary, employeeOutputDTO.getEmployeeSalary());
        assertEquals(employeeProjectID, employeeOutputDTO.getEmployeeProjectID());
        assertEquals(employeeDepartmentID, employeeOutputDTO.getEmployeeDepartmentID());
        assertEquals(role, employeeOutputDTO.getRole());
    }

    @Test
    public void testToEmployeeOutputDTO() {
        // Create an Employee
        Employee employee = new Employee("John Doe", "johndoe", "johndoe@example.com", "password");
        employee.setEmployeeId(1);
        employee.setEmployeeSalary(50000.0f);
        employee.setRole("USER");

        // Convert Employee to EmployeeOutputDTO
        EmployeeOutputDTO outputDTO = EmployeeOutputDTO.toEmployeeOutputDTO(employee);

        // Verify the conversion
        assertEquals(employee.getEmployeeId(), outputDTO.getEmployeeId());
        assertEquals(employee.getEmployeeName(), outputDTO.getEmployeeName());
        assertEquals(employee.getUsername(), outputDTO.getUserName());
        assertEquals(employee.getEmployeeSalary(), outputDTO.getEmployeeSalary());
        assertEquals(-1, outputDTO.getEmployeeProjectID()); // No project assigned
        assertEquals(-1, outputDTO.getEmployeeDepartmentID()); // No department assigned
        assertEquals(employee.getRole(), outputDTO.getRole());
    }

    @Test
    public void testToListEmployeesOutputDTO() {
        // Create a list of Employees
        List<Employee> employees = new ArrayList<>();
        Employee employee1 = new Employee("John Doe", "johndoe", "johndoe@example.com", "password");
        employee1.setEmployeeId(1);
        employee1.setEmployeeSalary(50000.0f);
        employee1.setRole("USER");
        employees.add(employee1);

        Employee employee2 = new Employee("Jane Smith", "janesmith", "janesmith@example.com", "password");
        employee2.setEmployeeId(2);
        employee2.setEmployeeSalary(60000.0f);
        employee2.setRole("ADMIN");
        employees.add(employee2);

        // Convert list of Employees to list of EmployeeOutputDTOs
        List<EmployeeOutputDTO> outputDTOs = EmployeeOutputDTO.toListEmployeesOutputDTO(employees);

        // Verify the conversion
        assertEquals(employees.size(), outputDTOs.size());
        assertEquals(employees.get(0).getEmployeeId(), outputDTOs.get(0).getEmployeeId());
        assertEquals(employees.get(1).getEmployeeId(), outputDTOs.get(1).getEmployeeId());
    }
}

public class ProjectOutputDTOTest {

    private ProjectOutputDTO projectOutputDTO;

    @BeforeEach
    public void setUp() {
        projectOutputDTO = new ProjectOutputDTO();
    }

    @Test
    public void testParameterizedConstructor() {
        int projectID = 1;
        int projectManagerID = 2;
        String projectName = "Project One";
        int projectDepartmentID = 3;

        List<EmployeeOutputDTO> projectEmployees = new ArrayList<>();
        EmployeeOutputDTO employeeOutputDTO = new EmployeeOutputDTO(1, "John Doe", "johndoe", 50000.0f, -1, -1, "USER");
        projectEmployees.add(employeeOutputDTO);

        projectOutputDTO = new ProjectOutputDTO(projectID, projectManagerID, projectName, projectEmployees, projectDepartmentID);

        assertEquals(projectID, projectOutputDTO.getProjectID());
        assertEquals(projectManagerID, projectOutputDTO.getProjectManagerID());
        assertEquals(projectName, projectOutputDTO.getProjectName());
        assertEquals(projectDepartmentID, projectOutputDTO.getProjectDepartmentID());
        assertEquals(projectEmployees, projectOutputDTO.getProjectEmployees());
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(projectOutputDTO);
    }

    @Test
    public void testGettersAndSetters() {
        int projectID = 1;
        int projectManagerID = 2;
        String projectName = "Project One";
        int projectDepartmentID = 3;

        List<EmployeeOutputDTO> projectEmployees = new ArrayList<>();
        EmployeeOutputDTO employeeOutputDTO = new EmployeeOutputDTO(1, "John Doe", "johndoe", 50000.0f, -1, -1, "USER");
        projectEmployees.add(employeeOutputDTO);

        projectOutputDTO.setProjectID(projectID);
        projectOutputDTO.setProjectManagerID(projectManagerID);
        projectOutputDTO.setProjectName(projectName);
        projectOutputDTO.setProjectDepartmentID(projectDepartmentID);
        projectOutputDTO.setProjectEmployees(projectEmployees);

        assertEquals(projectID, projectOutputDTO.getProjectID());
        assertEquals(projectManagerID, projectOutputDTO.getProjectManagerID());
        assertEquals(projectName, projectOutputDTO.getProjectName());
        assertEquals(projectDepartmentID, projectOutputDTO.getProjectDepartmentID());
        assertEquals(projectEmployees, projectOutputDTO.getProjectEmployees());
    }

    @Test
    public void testToProjectOutputDTO() {
        // Create a Project
        Project project = new Project("Project One", 1);
        project.setProjectID(1);
        project.setProjectManagerID(2);

        // Convert Project to ProjectOutputDTO
        ProjectOutputDTO outputDTO = ProjectOutputDTO.toProjectOutputDTO(project);

        // Verify the conversion
        assertEquals(project.getProjectID(), outputDTO.getProjectID());
        assertEquals(project.getProjectManagerID(), outputDTO.getProjectManagerID());
        assertEquals(project.getProjectName(), outputDTO.getProjectName());
        assertEquals(project.getProjectDepartment().getDepartmentID(), outputDTO.getProjectDepartmentID());
        assertEquals(0, outputDTO.getProjectEmployees().size()); // No employees assigned
    }

    @Test
    public void testToListProjectOutputDTO() {
        // Create a list of Projects
        List<Project> projects = new ArrayList<>();
        Project project1 = new Project("Project One", 1);
        project1.setProjectID(1);
        project1.setProjectManagerID(2);
        projects.add(project1);

        Project project2 = new Project("Project Two", 3);
        project2.setProjectID(2);
        project2.setProjectManagerID(4);
        projects.add(project2);

        // Convert list of Projects to list of ProjectOutputDTOs
        List<ProjectOutputDTO> outputDTOs = ProjectOutputDTO.toListProjectOutputDTO(projects);

        // Verify the conversion
        assertEquals(projects.size(), outputDTOs.size());
        assertEquals(projects.get(0).getProjectID(), outputDTOs.get(0).getProjectID());
        assertEquals(projects.get(1).getProjectID(), outputDTOs.get(1).getProjectID());
    }
}

public class ResponseObjectTest {

    private ResponseObject<String> responseObject;

    @BeforeEach
    public void setUp() {
        responseObject = new ResponseObject<>();
    }

    @Test
    public void testParameterizedConstructor() {
        String message = "Test message";
        int statusCode = 200;
        String data = "Test data";

        responseObject = new ResponseObject<>(message, statusCode, data);

        assertEquals(message, responseObject.getMessage());
        assertEquals(statusCode, responseObject.getStatusCode());
        assertEquals(data, responseObject.getData());
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(responseObject);
    }

    @Test
    public void testGettersAndSetters() {
        String message = "Test message";
        int statusCode = 200;
        String data = "Test data";

        responseObject.setMessage(message);
        responseObject.setStatusCode(statusCode);
        responseObject.setData(data);

        assertEquals(message, responseObject.getMessage());
        assertEquals(statusCode, responseObject.getStatusCode());
        assertEquals(data, responseObject.getData());
    }
}

public class AssignManagerDTOTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidAssignManagerDTO() {
        AssignManagerDTO assignManagerDTO = new AssignManagerDTO();
        assignManagerDTO.setEmployeeID(123);
        assignManagerDTO.setAssignToRole("Manager");
        assignManagerDTO.setAssignToRoleID(456);

        Set<ConstraintViolation<AssignManagerDTO>> violations = validator.validate(assignManagerDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testGettersAndSetters() {
        AssignManagerDTO assignManagerDTO = new AssignManagerDTO();
        int employeeID = 123;
        String assignToRole = "Manager";
        int assignToRoleID = 456;

        assignManagerDTO.setEmployeeID(employeeID);
        assignManagerDTO.setAssignToRole(assignToRole);
        assignManagerDTO.setAssignToRoleID(assignToRoleID);

        assertEquals(employeeID, assignManagerDTO.getEmployeeID());
        assertEquals(assignToRole, assignManagerDTO.getAssignToRole());
        assertEquals(assignToRoleID, assignManagerDTO.getAssignToRoleID());
    }
}

public class AssignUserDTOTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidAssignUserDTO() {
        AssignUserDTO assignUserDTO = new AssignUserDTO();
        assignUserDTO.setEmployeeID(123);
        assignUserDTO.setAssignToDepartmentID(456);

        Set<ConstraintViolation<AssignUserDTO>> violations = validator.validate(assignUserDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testGettersAndSetters() {
        AssignUserDTO assignUserDTO = new AssignUserDTO();
        int employeeID = 123;
        int assignToDepartmentID = 456;
        int assignToProjectID = 789;

        assignUserDTO.setEmployeeID(employeeID);
        assignUserDTO.setAssignToDepartmentID(assignToDepartmentID);
        assignUserDTO.setAssignToProjectID(assignToProjectID);

        assertEquals(employeeID, assignUserDTO.getEmployeeID());
        assertEquals(assignToDepartmentID, assignUserDTO.getAssignToDepartmentID());
        assertEquals(assignToProjectID, assignUserDTO.getAssignToProjectID());
    }
}

public class DepartmentInputDTOTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidDepartmentInputDTO() {
        DepartmentInputDTO departmentInputDTO = new DepartmentInputDTO();
        departmentInputDTO.setDepartmentName("Test Department");

        Set<ConstraintViolation<DepartmentInputDTO>> violations = validator.validate(departmentInputDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidDepartmentInputDTO() {
        DepartmentInputDTO departmentInputDTO = new DepartmentInputDTO();

        Set<ConstraintViolation<DepartmentInputDTO>> violations = validator.validate(departmentInputDTO);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size()); // Department Name is annotated with @NotNull
    }

    @Test
    public void testToDepartment() {
        DepartmentInputDTO departmentInputDTO = new DepartmentInputDTO();
        String departmentName = "Test Department";
        departmentInputDTO.setDepartmentName(departmentName);

        Department department = departmentInputDTO.toDepartment();

        assertNotNull(department);
        assertEquals(departmentName, department.getDepartmentName());
    }
}

public class EmployeeLoginInputDTOTest {

    @Test
    public void testEmployeeLoginInputDTO() {
        EmployeeLoginInputDTO employeeLoginInputDTO = new EmployeeLoginInputDTO();
        String username = "testuser";
        String password = "testpassword";

        // Set username and password
        employeeLoginInputDTO.setUsername(username);
        employeeLoginInputDTO.setPassword(password);

        // Verify getters return the correct values
        assertEquals(username, employeeLoginInputDTO.getUsername());
        assertEquals(password, employeeLoginInputDTO.getPassword());
    }
}

public class EmployeeRegisterInputDTOTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    public void testEmployeeRegisterInputDTO() {
        EmployeeRegisterInputDTO employeeRegisterInputDTO = new EmployeeRegisterInputDTO();
        String employeeName = "John Doe";
        String userName = "johndoe";
        String employeeEmail = "john@example.com";
        String password = "password";

        // Set values
        employeeRegisterInputDTO.setEmployeeName(employeeName);
        employeeRegisterInputDTO.setUserName(userName);
        employeeRegisterInputDTO.setEmployeeEmail(employeeEmail);
        employeeRegisterInputDTO.setPassword(password);

        // Validate constraints
        Set<ConstraintViolation<EmployeeRegisterInputDTO>> violations = validator.validate(employeeRegisterInputDTO);
        assertTrue(violations.isEmpty(), "No validation errors should be present");

        // Verify getters return the correct values
        assertEquals(employeeName, employeeRegisterInputDTO.getEmployeeName());
        assertEquals(userName, employeeRegisterInputDTO.getUserName());
        assertEquals(employeeEmail, employeeRegisterInputDTO.getEmployeeEmail());
        assertEquals(password, employeeRegisterInputDTO.getPassword());
    }

    @Test
    public void testConstraints() {
        EmployeeRegisterInputDTO employeeRegisterInputDTO = new EmployeeRegisterInputDTO();

        // Set values violating constraints
        employeeRegisterInputDTO.setEmployeeName(""); // Empty name
        employeeRegisterInputDTO.setUserName("us"); // Less than 3 characters
        employeeRegisterInputDTO.setEmployeeEmail("invalidemail"); // Invalid email format
        employeeRegisterInputDTO.setPassword("pass"); // Less than 8 characters

        // Validate constraints
        Set<ConstraintViolation<EmployeeRegisterInputDTO>> violations = validator.validate(employeeRegisterInputDTO);

        // Verify constraints are violated
        assertEquals(5, violations.size(), "There should be 5 violations");

        // Optionally, you can assert on specific constraint violations
        violations.forEach(violation -> {
            assertTrue(violation.getMessage().contains("Invalid"), "Error message should indicate invalid input");
        });
    }
}

public class ProjectEditDTOTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    public void testProjectEditDTO() {
        ProjectEditDTO projectEditDTO = new ProjectEditDTO();
        String projectName = "Project X";
        int projectDepartmentID = 1;

        // Set values
        projectEditDTO.setProjectName(projectName);
        projectEditDTO.setProjectDepartmentID(projectDepartmentID);

        // Validate constraints
        Set<ConstraintViolation<ProjectEditDTO>> violations = validator.validate(projectEditDTO);
        assertTrue(violations.isEmpty(), "No validation errors should be present");

        // Verify getters return the correct values
        assertEquals(projectName, projectEditDTO.getProjectName());
        assertEquals(projectDepartmentID, projectEditDTO.getProjectDepartmentID());
    }

    @Test
    public void testConstraints() {
        ProjectEditDTO projectEditDTO = new ProjectEditDTO();

        // Set values violating constraints
        projectEditDTO.setProjectName(""); // Empty project name

        // Validate constraints
        Set<ConstraintViolation<ProjectEditDTO>> violations = validator.validate(projectEditDTO);

        // Verify constraints are violated
        assertEquals(1, violations.size(), "There should be 1 violation");

        // Optionally, you can assert on specific constraint violations
        violations.forEach(violation -> {
            assertTrue(violation.getMessage().contains("Invalid"), "Error message should indicate invalid input");
        });
    }

    @Test
    public void testToProject() {
        ProjectEditDTO projectEditDTO = new ProjectEditDTO();
        String projectName = "Project X";
        int projectDepartmentID = 1;

        projectEditDTO.setProjectName(projectName);
        projectEditDTO.setProjectDepartmentID(projectDepartmentID);

        Department department = new Department("Department A");

        Project project = projectEditDTO.toProject(department);

        assertEquals(projectName, project.getProjectName());
        assertEquals(projectDepartmentID, project.getProjectDepartment().getDepartmentID());
        assertEquals(department, project.getProjectDepartment());
        assertNotNull(project.getProjectEmployees());
        assertEquals(0, project.getProjectEmployees().size());
    }
}

public class ProjectInputDTOTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    public void testProjectInputDTO() {
        ProjectInputDTO projectInputDTO = new ProjectInputDTO();
        String projectName = "Project X";
        int projectDepartmentID = 1;

        // Set values
        projectInputDTO.setProjectName(projectName);
        projectInputDTO.setProjectDepartmentID(projectDepartmentID);

        // Validate constraints
        Set<ConstraintViolation<ProjectInputDTO>> violations = validator.validate(projectInputDTO);
        assertTrue(violations.isEmpty(), "No validation errors should be present");

        // Verify getters return the correct values
        assertEquals(projectName, projectInputDTO.getProjectName());
        assertEquals(projectDepartmentID, projectInputDTO.getProjectDepartmentID());
    }

    @Test
    public void testConstraints() {
        ProjectInputDTO projectInputDTO = new ProjectInputDTO();

        // Set values violating constraints
        projectInputDTO.setProjectName(""); // Empty project name

        // Validate constraints
        Set<ConstraintViolation<ProjectInputDTO>> violations = validator.validate(projectInputDTO);

        // Verify constraints are violated
        assertEquals(1, violations.size(), "There should be 1 violation");

        // Optionally, you can assert on specific constraint violations
        violations.forEach(violation -> {
            assertTrue(violation.getMessage().contains("Invalid"), "Error message should indicate invalid input");
        });
    }

    @Test
    public void testToProject() {
        ProjectInputDTO projectInputDTO = new ProjectInputDTO();
        String projectName = "Project X";
        int projectDepartmentID = 1;

        projectInputDTO.setProjectName(projectName);
        projectInputDTO.setProjectDepartmentID(projectDepartmentID);

        Department department = new Department("Department A");

        Project project = projectInputDTO.toProject(department);

        assertEquals(projectName, project.getProjectName());
        assertEquals(projectDepartmentID, project.getProjectDepartment().getDepartmentID());
        assertEquals(department, project.getProjectDepartment());
        assertNotNull(project.getProjectEmployees());
        assertEquals(0, project.getProjectEmployees().size());
        assertEquals(-1, project.getProjectManagerID());
    }
}
