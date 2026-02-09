package com.reimbursement.reimbursementbackend.controller;

import com.reimbursement.reimbursementbackend.dto.RoleDto;
import com.reimbursement.reimbursementbackend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService service;

    @GetMapping
    public String index(Model model) {
        List<RoleDto> roles = service.getAll();
        model.addAttribute("roles", roles);
        return "role/index";
    }

    @GetMapping(value = {"form/{id}", "form"})
    public String form(Model model, @PathVariable(required = false) Integer id){
        if (id != null) {
            RoleDto role = service.get(id);
            model.addAttribute("role", role);
        } else {
            model.addAttribute("role", new RoleDto());
        }

        return "role/form";
    }

    @PostMapping("save")
    public String save(RoleDto roleDto) {
        service.save(roleDto);
        return "redirect:/role";
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/role";
    }

}
