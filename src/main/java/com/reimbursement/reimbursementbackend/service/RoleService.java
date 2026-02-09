package com.reimbursement.reimbursementbackend.service;

import com.reimbursement.reimbursementbackend.dto.RoleDto;
import com.reimbursement.reimbursementbackend.entity.Role;

import java.util.List;

public interface RoleService {
    RoleDto create(RoleDto dto);
    RoleDto update(Integer id, RoleDto dto);
    RoleDto save(RoleDto dto);
    RoleDto get(Integer id);
    Role getEntity(Integer id);
    List<RoleDto> getAll();
    void delete(Integer id);
}
