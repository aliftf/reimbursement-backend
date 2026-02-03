package com.reimbursement.reimbursementbackend.service;

import com.reimbursement.reimbursementbackend.dto.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto create(RoleDto dto);
    RoleDto update(Integer id, RoleDto dto);
    RoleDto get(Integer id);
    List<RoleDto> getAll();
    void delete(Integer id);
}
