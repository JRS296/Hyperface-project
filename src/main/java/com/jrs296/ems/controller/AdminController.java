package com.jrs296.ems.controller;

import com.jrs296.ems.models.DTOs.InputDTOs.AssignManagerDTO;
import com.jrs296.ems.models.DTOs.InputDTOs.AssignUserDTO;
import com.jrs296.ems.models.entity.Department;
import com.jrs296.ems.models.entity.Employee;
import com.jrs296.ems.models.entity.Project;
import com.jrs296.ems.service.DepartmentService;
import com.jrs296.ems.service.EmployeeService;
import com.jrs296.ems.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("api/assign")
public class AdminController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ProjectService projectService;

    @PutMapping("/manager")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DEPT_MANAGER')")
    public String assignManagerRole(@Valid @RequestBody AssignManagerDTO assignManagerDTO) {
        //Input employee ID (check if 0 and if it exists), assign type (ENUM of either DEPT_MANAGER or PROJECT_MANAGER), DEPT_ID (or) PROJECT_ID - Check if exists, if someone is already manager, deregister them and add them to employee pool of dept

        // Check if ADMIN or if DEPT_MANAGER, if ADMIN, can freely add to all. If DEPT_MANAGER, can only add PM's for their own Department

        //Check if emp ID is valid
        Employee employee = employeeService.getEmployeeByID(assignManagerDTO.getEmployeeID());
        if (employee==null) throw new RuntimeException("Employee Not Found");
        if (employee.getEmployeeId()==1) throw new RuntimeException("Cannot Assign ADMIN to role");

        //Check if Department or Project exists
        if (Objects.equals(assignManagerDTO.getAssignToRole(), "DEPT_MANAGER")) {
            Department department = departmentService.getDepartmentById(assignManagerDTO.getAssignToRoleID());
            if (department==null) throw new RuntimeException("Department Not Found");
        } else if (Objects.equals(assignManagerDTO.getAssignToRole(), "PROJECT_MANAGER")) {
            Project project = projectService.getProjectById(assignManagerDTO.getAssignToRoleID());
            if (project==null) throw new RuntimeException("Project Not Found");
        } else {
            throw new RuntimeException("Invalid Role to be assigned");
        }

        //Check if any Employee has already been registered for these projects/departments
        if (Objects.equals(assignManagerDTO.getAssignToRole(), "DEPT_MANAGER")) {
            Department department = departmentService.getDepartmentById(assignManagerDTO.getAssignToRoleID());
            if (department.getDepartmentManagerID()!=-1) {
                //Employee Changes
                Employee deptManager = employeeService.getEmployeeByID(department.getDepartmentManagerID());
                deptManager.setRole("USER");
                employeeService.saveEmployee(deptManager);

                //Department Changes
                department.getDepartmentEmployeesUnAssignedToProjects().add(deptManager);
                departmentService.saveDepartment(department);
            };
        } else if (Objects.equals(assignManagerDTO.getAssignToRole(), "PROJECT_MANAGER")) {
            Project project = projectService.getProjectById(assignManagerDTO.getAssignToRoleID());
            if (project.getProjectManagerID()!=-1) {
                //Employee Changes
                Employee projectManager = employeeService.getEmployeeByID(project.getProjectManagerID());
                projectManager.setRole("USER");
                projectManager.setEmployeeProject(null);

                //Project Changes
                project.setProjectManagerID(-1);
                projectService.saveProject(project);

                //Department Changes
                Department projectManagerDepartment = projectManager.getEmployeeDepartment();
                projectManagerDepartment.getDepartmentEmployeesUnAssignedToProjects().add(projectManager);
                departmentService.saveDepartment(projectManagerDepartment);
            }
        }

        //Update Employee
        employee.setRole(assignManagerDTO.getAssignToRole());
        if (Objects.equals(assignManagerDTO.getAssignToRole(), "DEPT_MANAGER")) {
            //Employee Changes
            employee.setEmployeeProject(null);
            employee.setRole("DEPT_MANAGER");
            employee.setEmployeeDepartment(departmentService.getDepartmentById(assignManagerDTO.getAssignToRoleID()));
            employeeService.saveEmployee(employee);

            //Department Changes
            Department department = departmentService.getDepartmentById(assignManagerDTO.getAssignToRoleID());
            department.setDepartmentManagerID(employee.getEmployeeId());
            departmentService.saveDepartment(department);
        } else {
            //Employee Changes
            employee.setEmployeeProject(projectService.getProjectById(assignManagerDTO.getAssignToRoleID()));
            employee.setRole("PROJECT_MANAGER");
            employee.setEmployeeDepartment(departmentService.getDepartmentById(projectService.getProjectById(assignManagerDTO.getAssignToRoleID()).getProjectDepartment().getDepartmentID()));
            employeeService.saveEmployee(employee);

            //Project Changes
            Project project = projectService.getProjectById(assignManagerDTO.getAssignToRoleID());
            project.setProjectManagerID(employee.getEmployeeId());
            projectService.saveProject(project);
        }

        return "ROLE CHANGE SUCCESSFUL";
    }

    @PutMapping("/assign/employee")
    @PreAuthorize("hasAnyAuthority('ADMIN','PROJECT_MANAGER', 'DEPT_MANAGER')")
    public String assignEmployeeRole(@Valid @RequestBody AssignUserDTO assignUserDTO) {
        //Check if Employee is Valid
        Employee employee = employeeService.getEmployeeByID(assignUserDTO.getEmployeeID());
        if (employee==null) throw new RuntimeException("Employee Not Found");
        if (employee.getEmployeeId()==1) throw new RuntimeException("Cannot Assign ADMIN to a USER based role.");

        //Check if Department or Project exists
        Department department = departmentService.getDepartmentById(assignUserDTO.getAssignToDepartmentID());
        if (department==null) throw new RuntimeException("Department Not Found");

        // If project is -1, assume that project is yet to be assigned - add employee to unassigned array

        //Remove Employee from current station
        //Change Employee fields
        employee.setEmployeeDepartment(null);

        //Change Department Fields

        //Change Project Fields
        employee.setEmployeeProject(null);

        //Add Employee to new

        return "EMPLOYEE ASSIGNMENT SUCCESSFUL";
    }
}
