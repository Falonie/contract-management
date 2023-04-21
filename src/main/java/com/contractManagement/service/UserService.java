package com.contractManagement.service;


import com.contractManagement.entity.User;
import com.contractManagement.entity.dto.UserRequest;

import java.util.Optional;

public interface UserService {
    User findUserByPhoneNum(Long id);
//    User findByUsername(UserRequest request);

    Optional<User> findByUsername(UserRequest request);

    Optional<User> findByUsernameAndPassword(UserRequest request);

    User register(String username, String password);
}
