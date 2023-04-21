package com.contractManagement.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.contractManagement.common.Result;
import com.contractManagement.enums.ResultEnum;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BindException.class})
    public Result<String> MethodArgumentNotValidExceptionHandler(BindException e) {
        // 从异常对象中拿到ObjectError对象
//        FieldError error = e.getFieldError();
//        String message = String.format("%s,%s", error.getField(), error.getDefaultMessage());
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return Result.error(objectError.getDefaultMessage());
    }

    @ExceptionHandler({ResultException.class})
    public Result<String> ResultExceptionHandler(ResultException e) {
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({NotLoginException.class})
    public Result<String> NotLoginExceptionHandler(NotLoginException e) {
        return Result.error(e.getMessage());
    }
}
