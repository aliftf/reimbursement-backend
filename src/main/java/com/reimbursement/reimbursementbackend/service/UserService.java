package com.reimbursement.reimbursementbackend.service;

import com.reimbursement.reimbursementbackend.dto.UserRequestDto;
import com.reimbursement.reimbursementbackend.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto save(UserRequestDto dto);
    UserResponseDto get(Integer id);
    List<UserResponseDto> getAll();
    void delete(Integer id);
}
