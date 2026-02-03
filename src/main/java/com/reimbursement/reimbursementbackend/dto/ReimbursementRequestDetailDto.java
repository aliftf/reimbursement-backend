package com.reimbursement.reimbursementbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReimbursementRequestDetailDto {
    private Integer id;
    private BigDecimal totalAmount;
    private LocalDateTime submissionDate;
    private Integer requesterId;
    private String requesterName;
    private List<ReimbursementItemDto> items;
    private ReimbursementRoadmapResponseDto roadmap;
}
