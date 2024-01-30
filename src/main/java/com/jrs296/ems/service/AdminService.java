package com.jrs296.ems.service;

import com.jrs296.ems.models.DTOs.InputDTOs.AssignManagerDTO;
import com.jrs296.ems.models.DTOs.InputDTOs.AssignUserDTO;
import com.jrs296.ems.models.entity.Department;
import com.jrs296.ems.models.entity.Employee;
import com.jrs296.ems.models.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminService {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ProjectService projectService;

    public String setManagerForDepartmentsAndProjects(AssignManagerDTO assignManagerDTO) {
        //Input employee ID (check if 0 and if it exists), assign type (ENUM of either DEPT_MANAGER or PROJECT_MANAGER), DEPT_ID (or) PROJECT_ID - Check if exists, if someone is already manager, deregister them and add them to employee pool of dept

        // Check if ADMIN or if DEPT_MANAGER, if ADMIN, can freely add to all. If DEPT_MANAGER, can only add PM's for their own Department
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        GrantedAuthority firstAuthority = authentication.getAuthorities().iterator().next();
        String firstAuthorityName = firstAuthority.getAuthority();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        //Strictly assuming that the Dept that the User belongs to is the DEPT in question
        Employee currentEmployee = employeeService.getEmployeeByUsername(userDetails.getUsername());
        int deptId = -1;
        if (currentEmployee.getEmployeeDepartment() != null) {
            deptId = currentEmployee.getEmployeeDepartment().getDepartmentID();
        }

        if (Objects.equals(firstAuthorityName, "ADMIN")) {
            //Check if emp ID is valid
            Employee employee = employeeService.getEmployeeByID(assignManagerDTO.getEmployeeID());
            if (employee == null) throw new RuntimeException("Employee Not Found");
            if (employee.getEmployeeId() == 1) throw new RuntimeException("Cannot Assign ADMIN to role");

            //Check if Department or Project exists
            if (Objects.equals(assignManagerDTO.getAssignToRole(), "DEPT_MANAGER")) {
                Department department = departmentService.getDepartmentById(assignManagerDTO.getAssignToRoleID());
                if (department == null) throw new RuntimeException("Department Not Found");
            } else if (Objects.equals(assignManagerDTO.getAssignToRole(), "PROJECT_MANAGER")) {
                Project project = projectService.getProjectById(assignManagerDTO.getAssignToRoleID());
                if (project == null) throw new RuntimeException("Project Not Found");
            } else {
                throw new RuntimeException("Invalid Role to be assigned");
            }

            //Check if any Employee has already been registered for these projects/departments
            if (Objects.equals(assignManagerDTO.getAssignToRole(), "DEPT_MANAGER")) {
                Department department = departmentService.getDepartmentById(assignManagerDTO.getAssignToRoleID());
                if (department.getDepartmentManagerID() != -1) {
                    //Employee Changes
                    Employee deptManager = employeeService.getEmployeeByID(department.getDepartmentManagerID());
                    deptManager.setRole("USER");
                    employeeService.saveEmployee(deptManager);

                    //Department Changes
                    department.getAllEmployees().add(deptManager);
                    departmentService.saveDepartment(department);
                }
            } else if (Objects.equals(assignManagerDTO.getAssignToRole(), "PROJECT_MANAGER")) {
                Project project = projectService.getProjectById(assignManagerDTO.getAssignToRoleID());

                if (project.getProjectDepartment().getDepartmentManagerID() == -1)
                    throw new RuntimeException("Department has no manager, Kindly assign a manager to given Department before proceeding.");


                if (project.getProjectManagerID() != -1) {
                    //Employee Changes
                    Employee projectManager = employeeService.getEmployeeByID(project.getProjectManagerID());
                    projectManager.setRole("USER");
                    projectManager.setEmployeeProject(null);

                    //Project Changes
                    project.setProjectManagerID(-1);
                    projectService.saveProject(project);

                    //Department Changes
                    Department projectManagerDepartment = projectManager.getEmployeeDepartment();
//                    projectManagerDepartment.getAllEmployees().add(projectManager);
                    projectManagerDepartment.setDepartmentManagerID(-1);
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
                department.getUnAssignedToProject().add(employee);
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

        } else {
            //DEPT MANAGER - get dept of which dept_manager is manager, then scope projects
            List<Project> listOfProjectsInDept = departmentService.getDepartmentById(deptId).getDepartmentProjects();
            List<Integer> listOfProjectsIDsInDept = listOfProjectsInDept.stream().map(Project::getProjectID).toList();

            //Check if emp ID is valid
            Employee employee = employeeService.getEmployeeByID(assignManagerDTO.getEmployeeID());
            if (employee == null) throw new RuntimeException("Employee Not Found");
            if (employee.getEmployeeId() == 1) throw new RuntimeException("Cannot Assign ADMIN to role");

            //Check if Trying to Assign a DEPT_MANAGER - NO
            if (Objects.equals(assignManagerDTO.getAssignToRole(), "DEPT_MANAGER")) {
                throw new RuntimeException("Department Manager Cannot assign another Department Manager");
            } else if (Objects.equals(assignManagerDTO.getAssignToRole(), "PROJECT_MANAGER")) {
                Project project = projectService.getProjectById(assignManagerDTO.getAssignToRoleID());
                if (project == null) throw new RuntimeException("Project Not Found");
                if (!listOfProjectsInDept.contains(project))
                    throw new RuntimeException("Cannot assign to a Project that is not in scope of current Department");
            } else {
                throw new RuntimeException("Invalid Role to be assigned");
            }

            //Check if any Employee has already been registered for these projects/departments
            Project project = projectService.getProjectById(assignManagerDTO.getAssignToRoleID());
            if (project.getProjectManagerID() != -1) {
                //Employee Changes
                Employee projectManager = employeeService.getEmployeeByID(project.getProjectManagerID());
                projectManager.setRole("USER");
                projectManager.setEmployeeProject(null);

                //Project Changes
                project.setProjectManagerID(-1);
                projectService.saveProject(project);

                //Department Changes
                Department projectManagerDepartment = projectManager.getEmployeeDepartment();
//                projectManagerDepartment.getAllEmployees().add(projectManager);
                departmentService.saveDepartment(projectManagerDepartment);
            }

            //Employee Changes
            employee.setEmployeeProject(projectService.getProjectById(assignManagerDTO.getAssignToRoleID()));
            employee.setRole("PROJECT_MANAGER");
            employee.setEmployeeDepartment(departmentService.getDepartmentById(projectService.getProjectById(assignManagerDTO.getAssignToRoleID()).getProjectDepartment().getDepartmentID()));
            employeeService.saveEmployee(employee);

            //Project Changes
            project.setProjectManagerID(employee.getEmployeeId());
            projectService.saveProject(project);
        }
        return "ROLE CHANGE SUCCESSFUL";
    }

    public String setEmployeeToDepartmentOrProject(AssignUserDTO assignUserDTO) {

        //If Admin - add freely
        //If Dept Manager - Can only add to own dept and projects
        //If Project Manager - Can only add to own projects

        //Check if Employee is Valid
        Employee employee = employeeService.getEmployeeByID(assignUserDTO.getEmployeeID());
        if (employee == null) throw new RuntimeException("Employee Not Found");
        if (employee.getEmployeeId() == 1) throw new RuntimeException("Cannot Assign ADMIN to a USER based role.");

        //Check if Department
        Department newDepartment = departmentService.getDepartmentById(assignUserDTO.getAssignToDepartmentID());
        if (newDepartment == null) throw new RuntimeException("Department Not Found");

        //Check if Dept, then Project has Managers, if not throw error
        if (newDepartment.getDepartmentID() == -1)
            throw new RuntimeException("Department has no manager, Kindly assign a manager to given Department before proceeding.");

        //Add Employee to new station
        // If project is -1, assume that project is yet to be assigned - add employee to new department's unassigned array
        // Else Add Employee to new station (set project and department to new) - update that project's employee list (add)
        employee.setEmployeeDepartment(newDepartment);

        //Check if Project (if specified) exists within Dept

        if(assignUserDTO.getAssignToProjectID()!=-1) {
            Project newProject = projectService.getProjectById(assignUserDTO.getAssignToProjectID());
            if (newProject == null) throw new RuntimeException("Project Not Found");

            if (!newDepartment.getDepartmentProjects().contains(newProject))
                throw new RuntimeException("Project specified is not a part of given Department");

            if (newProject.getProjectManagerID() == -1)
                throw new RuntimeException("Project has no manager, Kindly assign a manager to given Project before proceeding.");

            employee.setEmployeeProject(newProject);
            newProject.getProjectEmployees().add(employee);
            projectService.saveProject(newProject);
        } else {
            newDepartment.getUnAssignedToProject().add(employee);
            employee.setEmployeeDepartment(newDepartment);
            employee.setEmployeeProject(null);
        }
        employeeService.saveEmployee(employee);
        departmentService.saveDepartment(newDepartment);

        //Remove Employee from current station (set project and department null) - update that project's employee (remove)
        Project currentProject = employee.getEmployeeProject();
        if(currentProject!=null) {
            currentProject.getProjectEmployees().remove(employee);
            projectService.saveProject(currentProject);
        }

        return "EMPLOYEE ASSIGNMENT SUCCESSFUL";
    }
}
