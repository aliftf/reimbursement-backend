package com.reimbursement.reimbursementbackend.controller.api;

import com.reimbursement.reimbursementbackend.dto.AuthRequestDto;
import com.reimbursement.reimbursementbackend.service.jwt.JwtService;
import com.reimbursement.reimbursementbackend.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.reimbursement.reimbursementbackend.dto.RegisterDto;
import com.reimbursement.reimbursementbackend.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDto dto, @RequestHeader(name = "X-API-TOKEN") String token) {
        if (!token.equals("Testing")) { return ApiResponse.generate(HttpStatus.FORBIDDEN, "Unauthorized"); }

        boolean res = authService.register(dto);

        return res ? ApiResponse.generate(HttpStatus.CREATED, "Register success") : ApiResponse.generate(HttpStatus.BAD_REQUEST, "Register Failed");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticateAndGetToken(@RequestBody AuthRequestDto dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            return ApiResponse.generateToken(
                    HttpStatus.OK,
                    jwtService.generateToken(dto.getUsername())
            );
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

}
