package com.reimbursement.reimbursementbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_tr_reimbursement_item")
@Data @AllArgsConstructor @NoArgsConstructor
public class ReimbursementItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private BigDecimal amount = BigDecimal.ZERO;

    @Column(nullable = false)
    private LocalDateTime expenseDate;

    @Column(nullable = false)
    private String description = "-";

    @Column(nullable = false)
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "reimbursement_request_id", nullable = false)
    private ReimbursementRequest reimbursementRequest;
}
