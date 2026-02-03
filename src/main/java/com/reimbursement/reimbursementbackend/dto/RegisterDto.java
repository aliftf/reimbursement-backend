package com.reimbursement.reimbursementbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class RegisterDto {

    private String fullName;
    private String phoneNumber;
    private String personalEmail;
    private Integer departmentId;
    private Integer managerId;


    private String username;
    private String password;
    private String workEmail;
    private Integer roleId;

}
