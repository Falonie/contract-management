package com.contractManagement.exception;

import com.contractManagement.enums.ResultEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 返回异常类
 */
@Getter
@Setter
public class ResultException extends RuntimeException {
    private Integer code;
    private ResultEnum resultEnum;

    public ResultException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public ResultException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
