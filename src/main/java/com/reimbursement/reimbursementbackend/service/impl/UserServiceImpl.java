package com.reimbursement.reimbursementbackend.service.impl;

import com.reimbursement.reimbursementbackend.dto.EmployeeDto;
import com.reimbursement.reimbursementbackend.dto.RoleDto;
import com.reimbursement.reimbursementbackend.dto.UserRequestDto;
import com.reimbursement.reimbursementbackend.dto.UserResponseDto;
import com.reimbursement.reimbursementbackend.entity.Employee;
import com.reimbursement.reimbursementbackend.entity.Role;
import com.reimbursement.reimbursementbackend.entity.User;
import com.reimbursement.reimbursementbackend.exception.ApiException;
import com.reimbursement.reimbursementbackend.repository.EmployeeRepository;
import com.reimbursement.reimbursementbackend.repository.RoleRepository;
import com.reimbursement.reimbursementbackend.repository.UserRepository;
import com.reimbursement.reimbursementbackend.service.EmployeeService;
import com.reimbursement.reimbursementbackend.service.RoleService;
import com.reimbursement.reimbursementbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmployeeService employeeService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto save(UserRequestDto dto) {
        User u = new User();

        if (dto.getId() != null) {
            u = userRepository.findById(dto.getId())
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));

            if (dto.getPassword() != null &&
                    !dto.getPassword().isBlank()) {

                u.setPassword(
                        passwordEncoder.encode(dto.getPassword())
                );
            }
        } else {
            u.setPassword(
                    passwordEncoder.encode(dto.getPassword())
            );
        }

        Employee e = employeeService.getEntity(dto.getEmployee().getId());

        Role r = roleService.getEntity(dto.getRole().getId());

        u.setUsername(dto.getUsername());
        u.setWorkEmail(dto.getWorkEmail());
        u.setEmployee(e);
        u.setRole(r);

        u = userRepository.save(u);

        return toDto(u);
    }

    @Override
    public UserResponseDto get(Integer id) {

        User u = userRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));

        return toDto(u);
    }

    @Override
    public List<UserResponseDto> getAll() {
        return userRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public void delete(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new ApiException(HttpStatus.NOT_FOUND, "User not found");
        }

        userRepository.deleteById(id);
    }

//    private UserResponseDto toDto(User u, EmployeeDto e, RoleDto r) {
//        UserResponseDto user = new UserResponseDto();
//        user.setId(u.getId());
//        user.setUsername(u.getUsername());
//        user.setWorkEmail(u.getWorkEmail());
//        user.setEmployee(e);
//        user.setRole(r);
//
//        return user;
//    }

    private UserResponseDto toDto(User u) {

        EmployeeDto e = employeeService.get(u.getEmployee().getId());
        RoleDto r = roleService.get(u.getRole().getId());

        UserResponseDto user = new UserResponseDto();
        user.setId(u.getId());
        user.setUsername(u.getUsername());
        user.setWorkEmail(u.getWorkEmail());
        user.setEmployee(e);
        user.setRole(r);

        return user;
    }

//    private Employee toEmployee(EmployeeDto dto) {
//        Employee e = new Employee(dto.getId(), dto.getFullName(), dto.getPhoneNumber(), dto.getPersonalEmail(), dto.getDepartment(), dto.getManager())
//    }
}
