package com.reimbursement.reimbursementbackend.service;

import com.reimbursement.reimbursementbackend.dto.ReimbursementRoadmapRequestDto;
import com.reimbursement.reimbursementbackend.dto.ReimbursementRequestDetailDto;
import com.reimbursement.reimbursementbackend.dto.ReimbursementRoadmapResponseDto;
import com.reimbursement.reimbursementbackend.dto.SubmitReimbursementRequestDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface  ReimbursementService {
    ReimbursementRequestDetailDto submit(SubmitReimbursementRequestDto dto);
    ReimbursementRoadmapResponseDto decide(Integer requestId, ReimbursementRoadmapRequestDto dto, Integer statusId);

    List<ReimbursementRequestDetailDto> getEmployeeRequestList(Integer employeeId, Integer statusId, LocalDateTime fromDate, LocalDateTime toDate);
    List<ReimbursementRequestDetailDto> getManagerRequestList(Integer managerId, Integer statusId, LocalDateTime fromDate, LocalDateTime toDate);
}
