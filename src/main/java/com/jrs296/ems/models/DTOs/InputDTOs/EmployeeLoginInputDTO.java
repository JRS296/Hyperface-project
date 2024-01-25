package com.jrs296.ems.models.DTOs.InputDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class EmployeeLoginInputDTO { //Login

    private String username;
    private String password;

}
