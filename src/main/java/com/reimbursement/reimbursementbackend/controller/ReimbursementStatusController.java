package com.reimbursement.reimbursementbackend.controller;

import com.reimbursement.reimbursementbackend.dto.ReimbursementStatusDto;
import com.reimbursement.reimbursementbackend.service.ReimbursementStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("status")
@RequiredArgsConstructor
public class ReimbursementStatusController {
    private final ReimbursementStatusService service;

    @GetMapping
    public String index(Model model) {
        List<ReimbursementStatusDto> statuses = service.getAll();
        model.addAttribute("statuses", statuses);
        return "status/index";
    }

    @GetMapping(value = { "form/{id}", "form"})
    public String form(Model model, @PathVariable(required = false) Integer id) {
        if (id != null) {
            ReimbursementStatusDto status = service.get(id);
            model.addAttribute("status", status);
        } else {
            model.addAttribute("status", new ReimbursementStatusDto());
        }

        return "status/form";
    }

    @PostMapping("save")
    public String save(ReimbursementStatusDto dto) {
        service.save(dto);
        return "redirect:/status";
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/status";
    }
}
