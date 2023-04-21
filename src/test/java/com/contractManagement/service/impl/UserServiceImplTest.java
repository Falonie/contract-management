package com.contractManagement.service.impl;

import com.contractManagement.entity.User;
import com.contractManagement.entity.dto.UserRequest;
import com.contractManagement.enums.ResultEnum;
import com.contractManagement.exception.ResultException;
import com.contractManagement.repository.UserRepo;
import com.contractManagement.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceImplTest {
    @Resource
    private UserService userService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserRepo userRepo;

    @Test
    void findUserByPhoneNum() {
    }

    @Test
    @Transactional
    void findByUsername() {
        UserRequest request = new UserRequest();
        request.setUsername("testUser2");
        User user = userService.findByUsername(request).orElseThrow(() -> new ResultException(ResultEnum.USER_NOT_EXIST));
        user.setPassword(passwordEncoder.encode("7887cdma@"));
        User user1 = userRepo.save(user);
        assertNotNull(user1);
    }

    @Test
    void findByUsernameAndPassword() {
    }

    @Test
    void register() {
    }
}