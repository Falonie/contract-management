package com.contractManagement.service.impl;

import com.contractManagement.entity.User;
import com.contractManagement.entity.dto.UserRequest;
import com.contractManagement.enums.ResultEnum;
import com.contractManagement.exception.ResultException;
import com.contractManagement.repository.UserRepo;
import com.contractManagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 用户service
 */
@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findUserByPhoneNum(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new ResultException(ResultEnum.USER_NOT_EXIST));
    }

    @Override
    public Optional<User> findByUsername(UserRequest request) {
        return userRepo.findByUsername(request.getUsername());
    }

/*    @Override
    public User findByUsername(UserRequest request) {
        return userRepo.findByUsername(request.getUsername()).orElseThrow(() -> new ResultException(ResultEnum.USER_ALREADY_EXIST));
    }*/

    @Override
    public Optional<User> findByUsernameAndPassword(UserRequest request) {
//        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
//        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(username, password);
//        Authentication authentication = authenticationManager.authenticate(authReq);
//        AuthenticationContextHolder.setContext(authReq);
//        Authentication auth = authenticationManager.authenticate(authReq);
//        SecurityContext sc = SecurityContextHolder.getContext();
//        sc.setAuthentication(auth);
//        User user =
//        userRepo.findByUsername(request.getUsername() && bCryptPasswordEncoder.matches(request.getPassword(), ))

//        return userRepo.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        return userRepo.findByUsername(request.getUsername())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()));
    }

    /**
     * 注册
     *
     * @param username username
     * @param password password
     * @return user
     */
    @Override
    public User register(String username, String password) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .username(username)
                .password(password)
                .password(passwordEncoder.encode(password))
                .build();
        return userRepo.save(user);
    }
}
