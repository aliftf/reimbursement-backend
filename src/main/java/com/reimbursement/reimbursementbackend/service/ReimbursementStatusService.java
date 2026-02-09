package com.reimbursement.reimbursementbackend.service;

import com.reimbursement.reimbursementbackend.dto.ReimbursementStatusDto;

import java.util.List;

public interface ReimbursementStatusService {
    ReimbursementStatusDto create(ReimbursementStatusDto dto);
    ReimbursementStatusDto update(Integer id, ReimbursementStatusDto dto);
    ReimbursementStatusDto save(ReimbursementStatusDto dto);
    ReimbursementStatusDto get(Integer id);
    List<ReimbursementStatusDto> getAll();
    void delete(Integer id);
}
