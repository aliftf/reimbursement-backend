package com.reimbursement.reimbursementbackend.service;

import com.reimbursement.reimbursementbackend.dto.RegisterDto;

public interface AuthService {
    boolean register(RegisterDto dto);
}
