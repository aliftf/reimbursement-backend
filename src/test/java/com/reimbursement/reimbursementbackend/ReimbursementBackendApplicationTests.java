package com.reimbursement.reimbursementbackend;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.reimbursement.reimbursementbackend.entity.User;
import com.reimbursement.reimbursementbackend.repository.UserRepository;

@SpringBootTest
class ReimbursementBackendApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
        List<User> users = new ArrayList<>();
        users = userRepository.findAll();
        Assertions.assertEquals(5, users.size());
    }

    @Test
    void userRole() {
        String adminRole = "admin";
//        UserRoleDto userRoleDto = userRepository.findByUsernameUserRoleDTOs("alif");
//        Assertions.assertEquals(adminRole, userRoleDto.getRoleName());
    }
}
