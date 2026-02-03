package com.reimbursement.reimbursementbackend.repository;

import com.reimbursement.reimbursementbackend.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Query("select d from Department d where lower(d.name) = lower(:name)")
    Optional<Department> findByName(@Param("name") String name);
}
