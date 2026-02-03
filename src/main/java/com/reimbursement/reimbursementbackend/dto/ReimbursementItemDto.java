package com.reimbursement.reimbursementbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReimbursementItemDto {
    private Integer id;
    private BigDecimal amount;
    private LocalDateTime expenseDate;
    private String description;
    private String filePath;
}
