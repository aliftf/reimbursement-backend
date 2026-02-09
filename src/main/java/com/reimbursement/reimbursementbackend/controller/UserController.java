package com.reimbursement.reimbursementbackend.controller;

import com.reimbursement.reimbursementbackend.dto.EmployeeDto;
import com.reimbursement.reimbursementbackend.dto.RoleDto;
import com.reimbursement.reimbursementbackend.dto.UserRequestDto;
import com.reimbursement.reimbursementbackend.dto.UserResponseDto;
import com.reimbursement.reimbursementbackend.service.EmployeeService;
import com.reimbursement.reimbursementbackend.service.RoleService;
import com.reimbursement.reimbursementbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmployeeService employeeService;
    private final RoleService roleService;

    @GetMapping
    public String index(Model model) {
        List<UserResponseDto> users = userService.getAll();
        model.addAttribute("users", users);
        return "user/index";
    }

    @GetMapping(value = { "form/{id}", "form"})
    public String form(Model model, @PathVariable(required = false) Integer id) {
        List<RoleDto> roles = roleService.getAll();
        model.addAttribute("roles", roles);

        List<EmployeeDto> employees = employeeService.getAll();
        model.addAttribute("employees", employees);

        if (id != null) {
            UserResponseDto user = userService.get(id);
            UserRequestDto form = new UserRequestDto();

            form.setId(user.getId());
            form.setUsername(user.getUsername());
            form.setWorkEmail(user.getWorkEmail());
            form.setEmployee(user.getEmployee());
            form.setRole(user.getRole());

            model.addAttribute("user", form);
        } else {
            model.addAttribute("user", new UserRequestDto());
        }

        return "user/form";
    }

    @PostMapping("save")
    public String save(UserRequestDto userDto) {
        userService.save(userDto);

        return "redirect:/user";
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable Integer id) {
        userService.delete(id);
        return "redirect:/user";
    }
}
