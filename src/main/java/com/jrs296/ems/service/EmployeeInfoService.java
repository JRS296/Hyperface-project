package com.jrs296.ems.service;

import com.jrs296.ems.models.entity.Employee;
import com.jrs296.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class EmployeeInfoService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Employee> userDetail = employeeRepository.findByUsername(username);
        // Converting userDetail to UserDetails
        return userDetail.map(EmployeeInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(Employee employee) {
        employee.setEmployeePassword(encoder.encode(employee.getEmployeePassword()));
        employeeRepository.save(employee);
        return "User Added Successfully";
    }
}


