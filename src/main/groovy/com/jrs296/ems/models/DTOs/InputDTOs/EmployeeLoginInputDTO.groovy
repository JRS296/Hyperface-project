package com.jrs296.ems.models.DTOs.InputDTOs;

public class EmployeeLoginInputDTO { //Login

    private String username;
    private String password;

    String getUsername() {
        return username
    }

    void setUsername(String username) {
        this.username = username
    }

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }
}
