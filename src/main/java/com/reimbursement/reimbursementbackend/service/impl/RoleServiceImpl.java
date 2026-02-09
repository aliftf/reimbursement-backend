package com.reimbursement.reimbursementbackend.service.impl;

import com.reimbursement.reimbursementbackend.dto.RoleDto;
import com.reimbursement.reimbursementbackend.entity.Role;
import com.reimbursement.reimbursementbackend.exception.ApiException;
import com.reimbursement.reimbursementbackend.repository.RoleRepository;
import com.reimbursement.reimbursementbackend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleDto create(RoleDto dto) {
        roleRepository.findByName(dto.getName())
                .ifPresent(role -> {
                    throw new ApiException(HttpStatus.CONFLICT, "Role name already exists");
                });

        Role r = new Role();
        r.setName(dto.getName());
        r = roleRepository.save(r);

        return toDto(r);
    }

    @Override
    public RoleDto update(Integer id, RoleDto dto) {
        Role r = roleRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Role not found"));

        roleRepository.findByName(dto.getName())
                .filter(role -> !role.getId().equals(id))
                .ifPresent(role -> { throw new ApiException(HttpStatus.CONFLICT, "Role name already exists"); });

        r.setName(dto.getName());
        r = roleRepository.save(r);
        return toDto(r);
    }

    @Override
    public RoleDto save(RoleDto dto) {

        Role r;

        if (dto.getId() != null) {
            r = roleRepository.findById(dto.getId())
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Role not found"));
        } else {
            r = new Role();
        }

        roleRepository.findByName(dto.getName())
                .filter(role -> !role.getId().equals(dto.getId()))
                .ifPresent(role -> { throw new ApiException(HttpStatus.CONFLICT, "Role name already exists"); });

        r.setName(dto.getName());
        r = roleRepository.save(r);

        return toDto(r);
    }

    @Override
    public RoleDto get(Integer id) {
        Role r = roleRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Role not found"));
        return toDto(r);
    }

    @Override
    public Role getEntity(Integer id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Role not found"));
    }

    @Override
    public List<RoleDto> getAll() {
        return roleRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public void delete(Integer id) {
        if (!roleRepository.existsById(id)) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Role not found");
        }
        roleRepository.deleteById(id);
    }

    private RoleDto toDto(Role r) {
        return new RoleDto(r.getId(), r.getName());
    }
}
