package com.reimbursement.reimbursementbackend.repository;

import com.reimbursement.reimbursementbackend.entity.ReimbursementRoadmap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReimbursementRoadmapRepository extends JpaRepository<ReimbursementRoadmap, Integer> {
    @Query("""
    select rm from ReimbursementRoadmap rm
      join fetch rm.reimbursementStatus st
      join fetch rm.employee ac
    where rm.reimbursementRequest.id = :requestId
    order by rm.id asc
  """)
    List<ReimbursementRoadmap> findByRequestId(@Param("requestId") Long requestId);

    @Query("""
    select rm from ReimbursementRoadmap rm
    where rm.reimbursementRequest.id = :requestId
      and rm.id = (
        select max(rm2.id) from ReimbursementRoadmap rm2 where rm2.reimbursementRequest.id = :requestId
      )
  """)
    Optional<ReimbursementRoadmap> findLatest(@Param("requestId") Long requestId);
}
