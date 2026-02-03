package com.reimbursement.reimbursementbackend.repository;

import com.reimbursement.reimbursementbackend.entity.ReimbursementStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReimbursementStatusRepository extends JpaRepository<ReimbursementStatus, Integer> {

    @Query("select rs from ReimbursementStatus rs where lower(rs.name) = lower(:name)")
    Optional<ReimbursementStatus> findByName(@Param("name") String name);
}
