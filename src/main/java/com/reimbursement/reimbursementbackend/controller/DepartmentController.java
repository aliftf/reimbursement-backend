package com.reimbursement.reimbursementbackend.controller;

import com.reimbursement.reimbursementbackend.dto.DepartmentDto;
import com.reimbursement.reimbursementbackend.entity.Department;
import com.reimbursement.reimbursementbackend.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService service;

    @GetMapping
    public String index(Model model) {
        List<DepartmentDto> departments = service.getAll();
        model.addAttribute("departments", departments);
        return "department/index";
    }

    @GetMapping(value = { "form/{id}", "form"})
    public String form(Model model, @PathVariable(required = false) Integer id) {
        if (id != null) {
            DepartmentDto department = service.get(id);
            model.addAttribute("department", department);
        }  else {
            model.addAttribute("department", new DepartmentDto());
        }

        return "department/form";
    }

    @PostMapping("save")
    public String save(DepartmentDto departmentDto) {
        service.save(departmentDto);

        return "redirect:/department";
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/department";
    }
}
