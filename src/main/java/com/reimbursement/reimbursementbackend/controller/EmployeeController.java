package com.reimbursement.reimbursementbackend.controller;

import com.reimbursement.reimbursementbackend.dto.DepartmentDto;
import com.reimbursement.reimbursementbackend.dto.EmployeeDto;
import com.reimbursement.reimbursementbackend.dto.ManagerDto;
import com.reimbursement.reimbursementbackend.service.DepartmentService;
import com.reimbursement.reimbursementbackend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @GetMapping
    public String index(Model model) {
        List<EmployeeDto> employees = employeeService.getAll();
        model.addAttribute("employees", employees);
        return "employee/index";
    }

    @GetMapping(value = { "form/{id}", "form"})
    public String form(Model model, @PathVariable(required = false) Integer id) {
        List<DepartmentDto> departments = departmentService.getAll();
        model.addAttribute("departments", departments);

        List<EmployeeDto> managers = employeeService.getAll();
        model.addAttribute("managers", managers);

        if (id != null) {
            EmployeeDto employee = employeeService.get(id);
            model.addAttribute("employee", employee);
        }  else {
            model.addAttribute("employee", new EmployeeDto());
        }

        return "employee/form";
    }

    @PostMapping("save")
    public String save(EmployeeDto employeeDto) {



        employeeService.save(employeeDto);
        return "redirect:/employee";
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable Integer id) {
        employeeService.delete(id);
        return "redirect:/employee";
    }
}
