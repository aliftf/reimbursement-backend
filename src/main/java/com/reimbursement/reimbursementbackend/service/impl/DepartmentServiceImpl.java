package com.reimbursement.reimbursementbackend.service.impl;

import com.reimbursement.reimbursementbackend.dto.DepartmentDto;
import com.reimbursement.reimbursementbackend.entity.Department;
import com.reimbursement.reimbursementbackend.exception.ApiException;
import com.reimbursement.reimbursementbackend.repository.DepartmentRepository;
import com.reimbursement.reimbursementbackend.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public DepartmentDto create(DepartmentDto dto) {
        departmentRepository.findByName(dto.getName())
                .ifPresent(department -> {
                    throw new ApiException(HttpStatus.CONFLICT, "Department name already exists");
                });

        Department d = new Department();
        d.setName(dto.getName());
        d = departmentRepository.save(d);

        return toDto(d);
    }

    @Override
    public DepartmentDto update(Integer id, DepartmentDto dto) {
        Department d = departmentRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Department not found"));

        departmentRepository.findByName(dto.getName())
                .filter(department -> !department.getId().equals(id))
                .ifPresent(department -> { throw new ApiException(HttpStatus.CONFLICT, "Department name already exists"); });

        d.setName(dto.getName());
        d = departmentRepository.save(d);
        return toDto(d);
    }

    @Override
    public DepartmentDto save(DepartmentDto dto) {

        Department d;

        if (dto.getId() != null) {
            d = departmentRepository.findById(dto.getId())
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Department not found"));
        } else {
            d = new Department();
        }

        departmentRepository.findByName(dto.getName())
                .filter(department -> !department.getId().equals(dto.getId()))
                .ifPresent(department -> { throw new ApiException(HttpStatus.CONFLICT, "Department name already exists"); });

        d.setName(dto.getName());
        d = departmentRepository.save(d);
        return toDto(d);
    }

    @Override
    public DepartmentDto get(Integer id) {
        Department d = departmentRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Department not found"));
        return toDto(d);
    }

    @Override
    public List<DepartmentDto> getAll() {
        return departmentRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public void delete(Integer id) {
        if (!departmentRepository.existsById(id)) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Department not found");
        }
        departmentRepository.deleteById(id);
    }

    private DepartmentDto toDto(Department d) {
        return new DepartmentDto(d.getId(), d.getName());
    }
}
