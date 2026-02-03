package com.reimbursement.reimbursementbackend.service;

import com.reimbursement.reimbursementbackend.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    DepartmentDto create(DepartmentDto dto);
    DepartmentDto update(Integer id, DepartmentDto dto);
    DepartmentDto get(Integer id);
    List<DepartmentDto> getAll();
    void delete(Integer id);
}
