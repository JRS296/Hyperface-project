package com.jrs296.ems.controller;

import com.jrs296.ems.entity.Department;
import com.jrs296.ems.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/display")
    public String displayDepartment(@RequestBody Department department) {
        return departmentService.displayData(department);
    }

//    @PostMapping("")
//    public Department saveDepartment(@RequestBody Department department) {
//        return departmentService.saveDepartment(department);
//    }
//
//    @GetMapping("")
//    public List<Department> getAllDepartments() {
//        return departmentService.fetchAllDepartments();
//    }
//
//    @GetMapping("/{id}")
//    public Department getDepartmentById(@PathVariable("id") int id) {
//        return departmentService.getDepartmentById(id);
//    }
//
//    @PutMapping("/employee/{id}")
//    public Department updateDepartment(@PathVariable("id") int id, @RequestBody Department department) {
//        return departmentService.updateEmployeeById(id, department);
//    }

    @DeleteMapping("/{id}")
    public String deleteDepartment(@PathVariable("id") int id) {
        return departmentService.deleteDepartmentById(id);
    }
}
