package com.reimbursement.reimbursementbackend.service;

import com.reimbursement.reimbursementbackend.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto create(EmployeeDto dto);
    EmployeeDto update(Integer id, EmployeeDto dto);
    EmployeeDto get(Integer id);
    List<EmployeeDto> getAll();
    void delete(Integer id);
}
