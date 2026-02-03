package com.reimbursement.reimbursementbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_m_department")
@Data @AllArgsConstructor @NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;
}
