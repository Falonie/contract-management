package com.contractManagement.enums;

public enum ResultEnum {
    SUCCESS(200, "成功"),
    LOGIN_ERROR(500, "登录失败，请检查用户名或密码"),

    CHECK_LOGIN_ERROR(500, "请先登录登录失败"),
    USER_NOT_EXIST(405, "用户不存在"),

    USER_ALREADY_EXIST(406, "用户已存在"),

    CONTRACT_NOT_EXIST(407, "合同不存在"),
    PAYMENT_RECORD_NOT_EXIST(408, "该支付记录不存在"),

    CONTRACT_IMAGE_NOT_EXIST(409, "合同图片不存在"),
    CONTRACT_HAS_EXISTED(410, "合同已存在"),
    CLIENT_NOT_EXIST(411, "甲方客户不存在");

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
