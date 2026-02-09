package com.reimbursement.reimbursementbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDto {
    private Integer id;
    private String fullName;
    private String phoneNumber;
    private String personalEmail;
    private DepartmentDto department;
}
