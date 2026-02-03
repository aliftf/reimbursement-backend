package com.reimbursement.reimbursementbackend.controller;

import org.springframework.web.bind.annotation.*;

import com.reimbursement.reimbursementbackend.dto.RegisterDto;
import com.reimbursement.reimbursementbackend.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterDto dto, @RequestHeader(name = "X-API-TOKEN") String token) {
        if (!token.equals("Testing")) { return "Unauthorized"; }

        boolean res = authService.register(dto);

        return res ? "Register berhasil" : "Register gagal";
    }
}
