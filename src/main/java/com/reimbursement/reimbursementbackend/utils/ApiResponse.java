package com.reimbursement.reimbursementbackend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ApiResponse {
    public static ResponseEntity<Object> generate(HttpStatus httpStatus, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", httpStatus);
        response.put("message", message);
        return new ResponseEntity<Object>(response, httpStatus);
    }

    public static ResponseEntity<Object> generate(HttpStatus httpStatus, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", httpStatus);
        response.put("message", message);
        response.put("data", data);
        return new ResponseEntity<Object>(response, httpStatus);
    }

    public static ResponseEntity<Object> generateToken(HttpStatus httpStatus, String token) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", httpStatus);
        response.put("token", token);
        return new ResponseEntity<Object>(response, httpStatus);
    }
}
