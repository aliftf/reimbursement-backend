package com.reimbursement.reimbursementbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_tr_reimbursement_roadmap")
@Data @AllArgsConstructor @NoArgsConstructor
public class ReimbursementRoadmap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime approvalDate = LocalDateTime.now();

    @Column(nullable = false, length = 1000)
    private String note = "-";

    @ManyToOne
    @JoinColumn(name = "reimbursement_request_id", nullable = false)
    private ReimbursementRequest reimbursementRequest;

    @ManyToOne
    @JoinColumn(name = "reimbursement_status_id", nullable = false)
    private ReimbursementStatus reimbursementStatus;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
}
