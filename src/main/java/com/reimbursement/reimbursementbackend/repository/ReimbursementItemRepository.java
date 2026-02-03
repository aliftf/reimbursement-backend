package com.reimbursement.reimbursementbackend.repository;


import com.reimbursement.reimbursementbackend.entity.ReimbursementItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReimbursementItemRepository extends JpaRepository<ReimbursementItem, Integer> {
}
