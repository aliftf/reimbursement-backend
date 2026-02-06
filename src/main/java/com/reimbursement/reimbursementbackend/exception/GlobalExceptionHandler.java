package com.reimbursement.reimbursementbackend.exception;

import com.reimbursement.reimbursementbackend.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handle(ApiException ex) {
        return ApiResponse.generate(
                ex.getStatus(),
                ex.getMessage()
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handle(UsernameNotFoundException ex) {
        return ApiResponse.generate(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }
}
