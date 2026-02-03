package com.reimbursement.reimbursementbackend.repository;

import com.reimbursement.reimbursementbackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("select r from Role r where lower(r.name) = lower(:name)")
    Optional<Role> findByName(@Param("name") String name);
}
