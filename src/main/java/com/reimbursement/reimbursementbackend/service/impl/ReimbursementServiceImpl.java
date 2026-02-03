package com.reimbursement.reimbursementbackend.service.impl;

import com.reimbursement.reimbursementbackend.dto.*;
import com.reimbursement.reimbursementbackend.entity.*;
import com.reimbursement.reimbursementbackend.exception.ApiException;
import com.reimbursement.reimbursementbackend.repository.*;
import com.reimbursement.reimbursementbackend.service.ReimbursementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReimbursementServiceImpl implements ReimbursementService {

    private final EmployeeRepository employeeRepository;
    private final ReimbursementStatusRepository statusRepository;
    private final ReimbursementRequestRepository requestRepository;
    private final ReimbursementRoadmapRepository roadmapRepository;
    private final ReimbursementItemRepository itemRepository;
    private final ReimbursementRequestRepository reimbursementRequestRepository;

    @Override
    @Transactional
    public ReimbursementRequestDetailDto submit(SubmitReimbursementRequestDto dto) {
        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Request items must not be empty");
        }

        Employee requester = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Employee not found"));

        ReimbursementStatus status = statusRepository.findById(1)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Status not found"));

        BigDecimal totalAmount = dto.getItems().stream()
                .map(ReimbursementItemDto::getAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        ReimbursementRequest request = new ReimbursementRequest();
        request.setEmployee(requester);
        request.setSubmissionDate(LocalDateTime.now());
        request.setTotalAmount(totalAmount);

        request = requestRepository.save(request);

        for (ReimbursementItemDto itemDto : dto.getItems()) {
            ReimbursementItem item = new ReimbursementItem(
                    null,
                    itemDto.getAmount(),
                    itemDto.getExpenseDate(),
                    itemDto.getDescription(),
                    itemDto.getFilePath(),
                    request
            );

            item = itemRepository.save(item);

            request.getItems().add(item);
        }

        ReimbursementRoadmap roadmap = new ReimbursementRoadmap(
                null, request.getSubmissionDate(), "-", request, status, requester.getManager()
        );

        roadmap = roadmapRepository.save(roadmap);
        request.getRoadmap().add(roadmap);

        return toRequestDetailDto(request);
    }

    @Override
    public ReimbursementRoadmapResponseDto decide(Integer requestId, ReimbursementRoadmapRequestDto dto, Integer statusId) {
        ReimbursementRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Request not found"));

        Employee requesterManager = employeeRepository.findById(request.getEmployee().getManager().getId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Manager not found"));

        Employee approver = employeeRepository.findById(dto.getApproverId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Employee not found"));

        ReimbursementStatus status = statusRepository.findById(statusId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Reimbursement status not found"));

        if (!requesterManager.equals(approver)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Approver not equals");
        }

        ReimbursementRoadmap roadmap = new ReimbursementRoadmap(
                null,
                LocalDateTime.now(),
                dto.getNote(),
                request,
                status,
                approver
        );

        roadmap = roadmapRepository.save(roadmap);

        return toRoadmapDto(roadmap);
    }

    @Override
    public List<ReimbursementRequestDetailDto> getEmployeeRequestList(Integer employeeId, Integer statusId, LocalDateTime fromDate, LocalDateTime toDate) {

        return reimbursementRequestRepository.findByEmployeeId(employeeId, statusId, fromDate, toDate).stream()
                .map(this::toRequestDetailDto)
                .toList();
    }

    @Override
    public List<ReimbursementRequestDetailDto> getManagerRequestList(Integer managerId, Integer statusId, LocalDateTime fromDate, LocalDateTime toDate) {

        return reimbursementRequestRepository.findByManagerId(managerId, statusId, fromDate, toDate).stream()
                .map(this::toRequestDetailDto)
                .toList();
    }

    private ReimbursementRequestDetailDto toRequestDetailDto(ReimbursementRequest rr) {
        return new ReimbursementRequestDetailDto(
                rr.getId(),
                rr.getTotalAmount(),
                rr.getSubmissionDate(),
                rr.getEmployee().getId(),
                rr.getEmployee().getFullName(),
                rr.getItems().stream().map(this::toItemDto).toList(),
                toRoadmapDto(rr.getRoadmap().get(rr.getRoadmap().size() - 1))
        );
    }

    private ReimbursementItemDto toItemDto(ReimbursementItem item) {
        return new ReimbursementItemDto(
                item.getId(),
                item.getAmount(),
                item.getExpenseDate(),
                item.getDescription(),
                item.getFilePath()
        );
    }

    private ReimbursementRoadmapResponseDto toRoadmapDto(ReimbursementRoadmap roadmap) {

        return new ReimbursementRoadmapResponseDto(
                roadmap.getId(),
                roadmap.getApprovalDate(),
                roadmap.getNote(),
                roadmap.getReimbursementStatus().getName(),
                roadmap.getReimbursementRequest().getId(),
                roadmap.getEmployee().getId(),
                roadmap.getEmployee().getFullName()
        );
    }
}
