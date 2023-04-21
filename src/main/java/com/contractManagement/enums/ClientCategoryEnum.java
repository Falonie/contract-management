package com.contractManagement.enums;

import lombok.Getter;

/**
 * 项目客户类别
 */
@Getter
public enum ClientCategoryEnum {
    /**
     * 交通
     */
    TRANSPORTATION(1, "交通"),
    /**
     * 文旅
     */
    CULTURAL_TOURISM(2, "文旅"),
    /**
     * 商贸
     */
    BUSINESS(3, "商贸"),
    /**
     * 政务
     */
    GOVERNMENT(4, "政务"),
    /**
     * 军队
     */
    MILITARY(5, "军队"),
    /**
     * 银行
     */
    BANK(6, "银行"),
    /**
     * 国有企业
     */
    STATE_OWED_ENTERPRISE(7, "国有企业"),
    /**
     * 民营企业
     */
    PRIVATE_ENTERPRISE(8, "民营企业"),
    LOGISTICS(9, "数智后勤"),
    /**
     * 其它
     */
    OTHER(10, "其它");

    private final Integer code;
    private final String message;

    ClientCategoryEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
