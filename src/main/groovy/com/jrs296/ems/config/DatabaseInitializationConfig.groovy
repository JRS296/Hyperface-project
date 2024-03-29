package com.jrs296.ems.config;

import com.jrs296.ems.models.entity.Employee;
import com.jrs296.ems.repository.EmployeeRepository;
import com.jrs296.ems.service.EmployeeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DatabaseInitializationConfig {

    @Autowired
    private PasswordEncoder encoder;

    @Value('${populate.userinfo.onstartup:true}')
    boolean populateUserInfoOnStartup;

    @Bean
    CommandLineRunner initializeDatabase(EmployeeRepository employeeRepository, EmployeeInfoService employeeInfoService) {
        return { args ->
            if (populateUserInfoOnStartup && employeeRepository.count() == 0) {
                Employee admin = new Employee(
                        employeeName: "Admin",
                        role: "ADMIN",
                        employeePassword: "admin123",
                        username: "admin",
                        employeeEmail: "admin@ems.com"
                )
                employeeInfoService.addUser(admin)
            }
        }
    }
}
