package com.reimbursement.reimbursementbackend.service.impl;

import com.reimbursement.reimbursementbackend.dto.DepartmentDto;
import com.reimbursement.reimbursementbackend.dto.RegisterDto;
import com.reimbursement.reimbursementbackend.entity.Department;
import com.reimbursement.reimbursementbackend.entity.Employee;
import com.reimbursement.reimbursementbackend.entity.Role;
import com.reimbursement.reimbursementbackend.entity.User;
import com.reimbursement.reimbursementbackend.exception.ApiException;
import com.reimbursement.reimbursementbackend.repository.DepartmentRepository;
import com.reimbursement.reimbursementbackend.repository.EmployeeRepository;
import com.reimbursement.reimbursementbackend.repository.RoleRepository;
import com.reimbursement.reimbursementbackend.repository.UserRepository;
import com.reimbursement.reimbursementbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public boolean register(RegisterDto dto) {

        Employee manager = (dto.getManagerId() != null) ? employeeRepository.findById(dto.getManagerId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Manager not found")) : null;

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Department not found"));

        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Role not found"));

        Employee employee = new Employee(null, dto.getFullName(), dto.getPhoneNumber(), dto.getPersonalEmail(), department, manager);
        employee = employeeRepository.save(employee);

        User user = new User(null, dto.getUsername(), dto.getPassword(), dto.getWorkEmail(), employee, role);
        user = userRepository.save(user);

        return userRepository.findById(user.getId()).isPresent();
    }
}
