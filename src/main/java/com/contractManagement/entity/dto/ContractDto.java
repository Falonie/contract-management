package com.contractManagement.entity.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@With
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    @NotNull(message = "项目名称不能为空")
    private String projectName = "";
    /**
     * 项目合同类别
     * 1 承包合同， 2 采购合同、3 运营合作协议
     */
    private Integer contractCategory;
    /**
     * 项目客户类别
     * 1 交通、2 文旅、3 商贸、4 政务、5 军队、6 银行、7 国有企业、8 民营企业
     */
    private Integer clientCategory;
    @NotNull(message = "甲方名称不能为空")
    private String firstParty;
    @NotNull(message = "乙方名称不能为空")
    private String secondParty;
    private String signDate;
    private String totalAmount;
    private String paymentType;
    private String paidAmount;
    private String paymentRemark;
    private String firstPartyContractor;
    private String secondPartyContractor;
    private String firstPartyPhone;
    private String secondPartyPhone;
    private String scanningCopy;
    private String projectProgress;
    private String existingProblem;
    /**
     * 用户ID
     */
    private Long userId;
    private Integer pageSize = 10;
    private Integer pageNum = 1;

    private String startTotalAmount;
    private String endTotalAmount;
    /**
     * 查询区间范围
     * 1:0-50
     * 2:50-80
     * 3:80-100
     */
    private Integer progressRange;
    private Integer startProgress;
    private Integer endProgress;
}
