package com.contractManagement.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.contractManagement.common.Result;
import com.contractManagement.entity.User;
import com.contractManagement.entity.dto.UserRequest;
import com.contractManagement.enums.ResultEnum;
import com.contractManagement.exception.ResultException;
import com.contractManagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * 用户 controller
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 登录
     * @param request userRequest
     * @return tokenInfo
     */
    @PostMapping("/login")
    public Result<SaTokenInfo> login(@RequestBody UserRequest request) {
        Optional<User> user = userService.findByUsernameAndPassword(request);
        return user.map(value -> {
                    StpUtil.login(user.get().getId());
                    return Result.success("登录成功", StpUtil.getTokenInfo());
                })
                .orElseThrow(() -> new ResultException(ResultEnum.LOGIN_ERROR));
    }

    /**
     * 注册接口
     * @param request userRequest
     * @return user
     */
    @PostMapping("/register")
    public Result<User> register(@RequestBody UserRequest request) {
//        val user = buildUser(request);
        Optional<User> user = userService.findByUsername(request);
        if (user.isPresent()) {
            throw new ResultException(ResultEnum.USER_ALREADY_EXIST);
        }
//        userService.findByUsername(request);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        User user1 = userService.register(request.getUsername(), passwordEncoder.encode(request.getPassword()));
        User user1 = userService.register(request.getUsername(), request.getPassword());
        return Result.success(user1);
    }

    /**
     * 注销
     * @return
     */
    @GetMapping("logout")
    public Result<String> logout() {
        StpUtil.logout();
        return Result.success("退出成功");
    }

    /**
     * 获取tokenInfo
     * @return
     */
    @GetMapping("tokenInfo")
    public Result<SaTokenInfo> tokenInfo() {
        return Result.success(StpUtil.getTokenInfo());
    }


    private User buildUser(UserRequest request) {
        return User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();
    }
}
