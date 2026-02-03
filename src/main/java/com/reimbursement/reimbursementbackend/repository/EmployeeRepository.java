package com.reimbursement.reimbursementbackend.repository;

import com.reimbursement.reimbursementbackend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("select e from Employee e join e.department d left join e.manager m where e.id = :id")
    Optional<Employee> findById(@Param("id") Integer id);

    @Query("select e from Employee e where e.manager.id = :managerId")
    List<Employee> findByManagerId(@Param("managerId") Integer managerId);
}
