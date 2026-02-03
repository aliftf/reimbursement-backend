package com.reimbursement.reimbursementbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private Integer id;
    private String full_name;
    private String email;
    private DepartmentDto department;
    private EmployeeDto manager;
}
