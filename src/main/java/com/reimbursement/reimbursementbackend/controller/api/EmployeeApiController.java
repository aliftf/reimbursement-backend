package com.reimbursement.reimbursementbackend.controller.api;

import com.reimbursement.reimbursementbackend.dto.EmployeeResponseDto;
import com.reimbursement.reimbursementbackend.repository.EmployeeRepository;
import com.reimbursement.reimbursementbackend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeApiController {

    private final EmployeeRepository repository;

    @GetMapping
    public Map<String, Object> getAllEmployee(@RequestParam Map<String, String> requestParams) {
        Integer draw = Integer.parseInt(requestParams.get("draw"));
        Integer startPage = Integer.parseInt(requestParams.get("start")); // 1
        Integer length = Integer.parseInt(requestParams.get("length")); // total row'

        String searchValue = requestParams.get("search[value]");

        Page<EmployeeResponseDto> employees = repository.getEmployeePage(searchValue, PageRequest.of(startPage / length, length));

        Map<String, Object> response = new HashMap<>();
        response.put("draw", draw);
        response.put("recordsTotal", employees.getTotalElements());
        response.put("recordsFiltered", employees.getTotalElements());
        response.put("data", employees.getContent());

        return response;
    }

}
