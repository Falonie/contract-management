package com.contractManagement.enums;

import lombok.Getter;

/**
 * 项目合同类别
 */
@Getter
public enum ContractCategoryEnum {
    CONTRACT(1, "CONTRACT"),
    PURCHASE(2, "PURCHASE_CONTRACT"),
    OPERATION_CONTRACT(3, "OPERATION_CONTRACT"),
    OTHER(4,"OTHER");
    private final Integer code;
    private final String message;

    ContractCategoryEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
