package com.reimbursement.reimbursementbackend.service;

import com.reimbursement.reimbursementbackend.dto.EmployeeDto;
import com.reimbursement.reimbursementbackend.entity.Employee;

import java.util.List;

public interface EmployeeService {
    EmployeeDto save(EmployeeDto dto);
    EmployeeDto create(EmployeeDto dto);
    EmployeeDto update(Integer id, EmployeeDto dto);
    EmployeeDto get(Integer id);
    Employee getEntity(Integer id);
    List<EmployeeDto> getAll();
    void delete(Integer id);
}
