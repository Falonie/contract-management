package com.contractManagement.common;

import com.contractManagement.enums.ResultEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.BindingResult;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class Result<T> {
    private int code;
    private String msg;
    private T data;
    private ResultEnum resultEnum;

    public Result(T data) {
        this.data = data;
    }

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(ResultEnum resultEnum) {
        this.resultEnum = resultEnum;
//        super(resultEnum.getMessage());
//        this.code = resultEnum.getCode();
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.code == ResponseCode.SUCCESS.getCode();
    }

    public static <T> Result<T> success(String message) {
        return new Result<>(ResponseCode.SUCCESS.getCode(), message);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc(), data);
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> Result<T> error(String message, T data) {
        return new Result<>(ResponseCode.ERROR.getCode(), message, data);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(ResponseCode.ERROR.getCode(), message);
    }

    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message);
    }

    public static <T> Result<T> error(T data) {
        return new Result<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc(), data);
    }

    public static <T> Result<T> failByMsgGenBindingResult(BindingResult bindingResult) {
        return new Result<>(ResponseCode.ERROR.getCode(),
                Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }
}
