package com.reimbursement.reimbursementbackend.service.impl;

import com.reimbursement.reimbursementbackend.dto.DepartmentDto;
import com.reimbursement.reimbursementbackend.dto.ReimbursementStatusDto;
import com.reimbursement.reimbursementbackend.entity.Department;
import com.reimbursement.reimbursementbackend.entity.ReimbursementStatus;
import com.reimbursement.reimbursementbackend.exception.ApiException;
import com.reimbursement.reimbursementbackend.repository.ReimbursementStatusRepository;
import com.reimbursement.reimbursementbackend.service.ReimbursementStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReimbursementStatusServiceImpl implements ReimbursementStatusService {

    @Autowired
    private ReimbursementStatusRepository reimbursementStatusRepository;

    @Override
    public ReimbursementStatusDto create(ReimbursementStatusDto dto) {
        reimbursementStatusRepository.findByName(dto.getName())
                .ifPresent(department -> {
                    throw new ApiException(HttpStatus.CONFLICT, "Reimbursement status name already exists");
                });

        ReimbursementStatus rs = new ReimbursementStatus();
        rs.setName(dto.getName());
        rs = reimbursementStatusRepository.save(rs);

        return toDto(rs);
    }

    @Override
    public ReimbursementStatusDto update(Integer id, ReimbursementStatusDto dto) {
        ReimbursementStatus rs = reimbursementStatusRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Reimbursement status not found"));

        reimbursementStatusRepository.findByName(dto.getName())
                .filter(department -> !department.getId().equals(id))
                .ifPresent(department -> { throw new ApiException(HttpStatus.CONFLICT, "Reimbursement status name already exists"); });

        rs.setName(dto.getName());
        rs = reimbursementStatusRepository.save(rs);
        return toDto(rs);
    }

    @Override
    public ReimbursementStatusDto get(Integer id) {
        ReimbursementStatus rs = reimbursementStatusRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Reimbursement status not found"));
        return toDto(rs);
    }

    @Override
    public List<ReimbursementStatusDto> getAll() {
        return reimbursementStatusRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public void delete(Integer id) {
        if (!reimbursementStatusRepository.existsById(id)) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Reimbursement status not found");
        }
        reimbursementStatusRepository.deleteById(id);
    }

    private ReimbursementStatusDto toDto(ReimbursementStatus rs) {
        return new ReimbursementStatusDto(rs.getId(), rs.getName());
    }
}
