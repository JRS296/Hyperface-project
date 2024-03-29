package com.jrs296.ems.models.DTOs.InputDTOs;

import com.jrs296.ems.models.entity.Employee
import groovy.transform.Canonical;
import jakarta.validation.constraints.*;

@Canonical
public class EmployeeRegisterInputDTO {
    @NotBlank(message = "Invalid Name: Empty name")
    @NotNull(message = "Invalid Name: Name is NULL")
    @Size(min = 3, max = 30, message = "Invalid Name: Must be of 3 - 30 characters")
    private String employeeName;

    @NotBlank(message = "Invalid user-name: Empty user-name")
    @NotNull(message = "Invalid user-name: user-name is NULL")
    @Size(min = 3, max = 30, message = "Invalid user-name: Must be of 3 - 30 characters")
    private String userName;

    @Email(message = "Invalid email")
    private String employeeEmail;

    @NotBlank(message = "Invalid password: Empty password")
    @NotNull(message = "Invalid password: Password is NULL")
    @Size(min = 8, max = 30, message = "Invalid password: Must be of a minimum of 8 characters")
//    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()-_+=])[a-zA-Z0-9!@#$%^&*()-_+=]{8,}$", message = "Invalid password, must have a minimum of 8 characters, at least 1 Uppercase, at least 1 number and at least 1 special character")
    /*
    ^: Asserts the start of the string.
    (?=.*[A-Z]): Positive lookahead assertion for at least one uppercase letter.
    (?=.*[0-9]): Positive lookahead assertion for at least one digit.
    (?=.*[!@#$%^&*()-_+=]): Positive lookahead assertion for at least one special character. You can customize the set of special characters inside the square brackets as needed.
    [a-zA-Z0-9!@#$%^&*()-_+=]{8,}: Matches any combination of uppercase letters, lowercase letters, digits, and specified special characters with a minimum length of 8 characters.
    $: Asserts the end of the string.
    */
    private String password;

    private int employeeProjectID = -1; //Can be null, if provided, is to be verified along with employeeDepartmentID

    public Employee toEmployee() {
        return new Employee(employeeName, userName, employeeEmail, password);
    }

    public EmployeeRegisterInputDTO() {}

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEmployeeProjectID() {
        return employeeProjectID;
    }

    public void setEmployeeProjectID(int employeeProjectID) {
        this.employeeProjectID = employeeProjectID;
    }
}
