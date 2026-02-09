package com.reimbursement.reimbursementbackend.repository;

import com.reimbursement.reimbursementbackend.dto.EmployeeDto;
import com.reimbursement.reimbursementbackend.dto.EmployeeResponseDto;
import com.reimbursement.reimbursementbackend.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("select new com.reimbursement.reimbursementbackend.dto.EmployeeResponseDto(" +
            "e.id, e.fullName, e.phoneNumber, e.personalEmail, e.department.name, e.manager.fullName) " +
            "from Employee e join e.department d left join e.manager m where :search is null or cast(e.id as string) like concat('%',:search,'%') " +
            "or e.fullName like concat('%',:search,'%') or e.personalEmail like concat('%',:search,'%') " +
            " or e.department.name like concat('%',:search,'%') or e.manager.fullName like concat('%',:search,'%') ")
    Page<EmployeeResponseDto> getEmployeePage(@Param("search") String search, Pageable pageable);
}
