package com.reimbursement.reimbursementbackend.controller;

import com.reimbursement.reimbursementbackend.dto.DepartmentDto;
import com.reimbursement.reimbursementbackend.service.DepartmentService;
import com.reimbursement.reimbursementbackend.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody DepartmentDto dto) {
        DepartmentDto newDto = departmentService.create(dto);
        return ApiResponse.generate(HttpStatus.CREATED, "New department created", newDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id, @RequestBody DepartmentDto dto) {
        DepartmentDto newDto = departmentService.update(id, dto);
        return ApiResponse.generate(HttpStatus.OK, "Department updated", newDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> get(@PathVariable Integer id) {
        DepartmentDto dto = departmentService.get(id);
        return ApiResponse.generate(HttpStatus.OK, "Department fetched", dto);
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<DepartmentDto> dtos = departmentService.getAll();
        return ApiResponse.generate(HttpStatus.OK, "All departments fetched", dtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        departmentService.delete(id);
        return ApiResponse.generate(HttpStatus.CREATED, "Departmend deleted");
    }

//    @GetMapping
//    public DepartmentDto getByParam(@RequestParam(name = "id") Integer id) {
//        return departmentService.get(id);
//    }
}
