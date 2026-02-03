package com.reimbursement.reimbursementbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor
public class ReimbursementRoadmapResponseDto {
    private Integer id;
    private LocalDateTime approvalDate;
    private String note;
    private String status;
    private Integer requestId;
    private Integer approverId;
    private String approverName;
}
