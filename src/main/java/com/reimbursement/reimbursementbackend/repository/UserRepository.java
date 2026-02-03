package com.reimbursement.reimbursementbackend.repository;

import com.reimbursement.reimbursementbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u join u.role join u.employee e where u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);


}
