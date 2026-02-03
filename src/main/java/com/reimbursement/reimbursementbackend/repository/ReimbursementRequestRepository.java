package com.reimbursement.reimbursementbackend.repository;

import com.reimbursement.reimbursementbackend.entity.ReimbursementRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReimbursementRequestRepository extends JpaRepository<ReimbursementRequest, Integer> {

    @Query("""
        select rr from ReimbursementRequest rr
        join rr.employee e
        left join rr.items
        left join rr.roadmap
        where rr.id = :id
    """)
    Optional<ReimbursementRequest> findById(@Param("id") Integer id);

    @Query("""
        select rr
        from ReimbursementRequest rr
        where rr.employee.id = :employeeId
        and exists (
            select 1
            from ReimbursementRoadmap rm
            where rm.reimbursementRequest.id = rr.id
            and rm.id = (
                select max(rm2.id)
                from ReimbursementRoadmap rm2
                where rm2.reimbursementRequest.id = rr.id
            )
            and (:statusId is null or rm.reimbursementStatus.id = :statusId)
            and (:fromDate is null or rm.approvalDate >= :fromDate)
            and (:toDate is null or rm.approvalDate <= :toDate)
        )
    """)
    List<ReimbursementRequest> findByEmployeeId(
            @Param("employeeId") Integer employeeId,
            @Param("statusId") Integer statusId,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );

    @Query("""
        select rr
        from ReimbursementRequest rr
        where rr.employee.manager.id = :managerId
        and exists (
            select 1
            from ReimbursementRoadmap rm
            where rm.reimbursementRequest.id = rr.id
            and rm.id = (
                select max(rm2.id)
                from ReimbursementRoadmap rm2
                where rm2.reimbursementRequest.id = rr.id
            )
            and (:statusId is null or rm.reimbursementStatus.id = :statusId)
            and (:fromDate is null or rm.approvalDate >= :fromDate)
            and (:toDate is null or rm.approvalDate <= :toDate)
        )
    """)
    List<ReimbursementRequest> findByManagerId(
            @Param("managerId") Integer managerId,
            @Param("statusId") Integer statusId,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );
}
