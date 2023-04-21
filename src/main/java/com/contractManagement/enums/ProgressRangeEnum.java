package com.contractManagement.enums;

import lombok.Getter;

/**
 * 查询范围区间
 */
@Getter
public enum ProgressRangeEnum {
    ZERO_TO_FIFTY(1, "0-50", 0, 50),
    FIFTY_TO_EIGHTY(2, "50-80", 50, 80),
    EIGHTY_TO_HUNDRED(3, "80-100", 80, 100);
    private final Integer code;
    private final String Range;
    private final Integer start;
    private final Integer end;

    ProgressRangeEnum(Integer code, String range, Integer start, Integer end) {
        this.code = code;
        Range = range;
        this.start = start;
        this.end = end;
    }
}
