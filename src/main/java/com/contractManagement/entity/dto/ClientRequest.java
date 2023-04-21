package com.contractManagement.entity.dto;

import lombok.*;

/**
 * 甲方客户信息管理 VO
 */
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {
    private Long id;
    private String unitName;
    private String unitAddress;
    private String unitPrincipal;
    private String unitPrincipalPhoneNum;
    private String officeNum;
    private String informationPrinciple;
    private String informationPrinciplePhoneNum;
    private Integer businessCategory;
    private Integer pageSize = 10;
    private Integer pageNum = 1;
}
