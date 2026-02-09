package com.reimbursement.reimbursementbackend.controller.api;

import com.reimbursement.reimbursementbackend.dto.ReimbursementRoadmapRequestDto;
import com.reimbursement.reimbursementbackend.dto.SubmitReimbursementRequestDto;
import com.reimbursement.reimbursementbackend.service.ReimbursementService;
import com.reimbursement.reimbursementbackend.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/reimbursements")
@RequiredArgsConstructor
public class ReimbursementController {

    private final ReimbursementService reimbursementService;

    @PostMapping("submit")
    public ResponseEntity<Object> submit(@RequestBody SubmitReimbursementRequestDto dto) {
        return ApiResponse.generate(
                HttpStatus.CREATED,
                "Reimbusement request submitted",
                reimbursementService.submit(dto)
        );
    }

    @PostMapping("{id}/approve")
    public ResponseEntity<Object> approve(@PathVariable Integer id, @RequestBody ReimbursementRoadmapRequestDto dto) {
        return ApiResponse.generate(
                HttpStatus.CREATED,
                "Reimbursement request approved",
                reimbursementService.decide(id, dto, 2)
        );
    }

    @PostMapping("{id}/reject")
    public ResponseEntity<Object> reject(@PathVariable Integer id, @RequestBody ReimbursementRoadmapRequestDto dto) {
        return ApiResponse.generate(
                HttpStatus.CREATED,
                "Reimbursement request rejected",
                reimbursementService.decide(id, dto, 3)
        );
    }

    @GetMapping("my/{employeeId}")
    public ResponseEntity<Object> getAllEmployeeRequest(
            @PathVariable Integer employeeId,
            @RequestParam(required = false) Integer statusId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
            ) {

        LocalDateTime fromDateTime = (fromDate != null) ? fromDate.atStartOfDay() : null;
        LocalDateTime toDateTime = (toDate != null) ? toDate.atTime(LocalTime.MAX) : null;

        return ApiResponse.generate(
                HttpStatus.OK,
                "Employee reimbursement request fetched",
                reimbursementService.getEmployeeRequestList(employeeId, statusId, fromDateTime, toDateTime)
        );
    }

    @GetMapping("inbox/{managerId}")
    public ResponseEntity<Object> getAllManagerRequest(
            @PathVariable Integer managerId,
            @RequestParam(required = false) Integer statusId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
    ) {

        LocalDateTime fromDateTime = (fromDate != null) ? fromDate.atStartOfDay() : null;
        LocalDateTime toDateTime = (toDate != null) ? toDate.atTime(LocalTime.MAX) : null;

        return ApiResponse.generate(
                HttpStatus.OK,
                "Manager reimbursement request fetched",
                reimbursementService.getManagerRequestList(managerId, statusId, fromDateTime, toDateTime)
        );
    }

}
