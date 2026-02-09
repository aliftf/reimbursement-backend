package com.reimbursement.reimbursementbackend.service.impl;

import com.reimbursement.reimbursementbackend.dto.DepartmentDto;
import com.reimbursement.reimbursementbackend.dto.EmployeeDto;
import com.reimbursement.reimbursementbackend.dto.ManagerDto;
import com.reimbursement.reimbursementbackend.entity.Department;
import com.reimbursement.reimbursementbackend.entity.Employee;
import com.reimbursement.reimbursementbackend.exception.ApiException;
import com.reimbursement.reimbursementbackend.repository.DepartmentRepository;
import com.reimbursement.reimbursementbackend.repository.EmployeeRepository;
import com.reimbursement.reimbursementbackend.service.DepartmentService;
import com.reimbursement.reimbursementbackend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final DepartmentService departmentService;

    @Override
    public EmployeeDto save(EmployeeDto dto) {
        Employee e;

        if (dto.getId() != null) {
            e = employeeRepository.findById(dto.getId())
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Employee not found"));
        } else {
            e = new Employee();
        }

        Department d = departmentRepository.findById(dto.getDepartment().getId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Department not found"));

        Employee m = null;

        if (dto.getManager().getId() != null) {
            m = employeeRepository.findById(dto.getManager().getId())
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Manager not found" + dto.getManager()));
        }

        e.setFullName(dto.getFullName());
        e.setPhoneNumber(dto.getPhoneNumber());
        e.setPersonalEmail(dto.getPersonalEmail());
        e.setDepartment(d);
        e.setManager(m);

        e = employeeRepository.save(e);

        return toDto(e);
    }

    @Override
    public EmployeeDto create(EmployeeDto dto) {
        return null;
    }

    @Override
    public EmployeeDto update(Integer id, EmployeeDto dto) {
        return null;
    }

    @Override
    public EmployeeDto get(Integer id) {

        Employee d = employeeRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Employee not found"));

        return toDto(d);
    }

    @Override
    public Employee getEntity(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Employee not found"));
    }

    @Override
    public List<EmployeeDto> getAll() {
        return employeeRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public void delete(Integer id) {
        if (!employeeRepository.existsById(id)) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Employee not found");
        }

        employeeRepository.deleteById(id);
    }

    public EmployeeDto toDto(Employee e) {

        DepartmentDto departmentDto = departmentService.get(e.getDepartment().getId());

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(e.getId());
        employeeDto.setFullName(e.getFullName());
        employeeDto.setPhoneNumber(e.getPhoneNumber());
        employeeDto.setPersonalEmail(e.getPersonalEmail());
        employeeDto.setDepartment(departmentDto);

        if (e.getManager() != null) {
            DepartmentDto managerDepartmentDto = departmentService.get(e.getManager().getDepartment().getId());

            ManagerDto managerDto = new ManagerDto();
            managerDto.setId(e.getManager().getId());
            managerDto.setFullName(e.getManager().getFullName());
            managerDto.setPhoneNumber(e.getManager().getPhoneNumber());
            managerDto.setPersonalEmail(e.getManager().getPersonalEmail());
            managerDto.setDepartment(managerDepartmentDto);

            employeeDto.setManager(managerDto);
        }

        return employeeDto;


    }
}
