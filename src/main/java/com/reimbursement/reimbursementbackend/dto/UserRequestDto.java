package com.reimbursement.reimbursementbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class UserRequestDto {

    private Integer id;
    private String username;
    private String password;
    private String workEmail;
    private EmployeeDto employee;
    private RoleDto role;
}
