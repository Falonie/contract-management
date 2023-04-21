package com.contractManagement.entity;

import lombok.*;

import javax.persistence.*;

/**
 * 合同实体类
 */
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 项目名称
     */
//    @NotNull(message = "项目名称不能为空")
    private String projectName;
    /**
     * 项目合同类别
     * 1 承包合同， 2 采购合同、3 运营合作协议
     */
    private Integer contractCategory;
    /**
     * 项目客户类别
     * 1 交通、2 文旅、3 商贸、4 政务、5 军队、6 银行、7 国有企业、8 民营企业 9 数智后勤 10 其它
     */
    private Integer clientCategory;
    /**
     * 甲方名称
     */
//    @NotNull(message = "甲方名称不能为空")
    private String firstParty;
    /**
     * 乙方名称
     */
//    @NotNull(message = "乙方名称不能为空")
    private String secondParty;
    /**
     * 合同签订日期
     */
    private String signDate;
    /**
     * 合同总额
     */
    private String totalAmount;
    /**
     * 支付方式
     */
    private String paymentType;
    /**
     * 已支付金额
     */
    private String paidAmount;
    /**
     * 未支付金额
     */
    private String unpaidAmount;
    /**
     * 支付备注
     */
    private String paymentRemark;
    /**
     * 合同甲方联系人
     */
    private String firstPartyContractor;
    /**
     * 甲方联系电话
     */
    private String secondPartyContractor;
    /**
     * 合同乙方联系人
     */
    private String firstPartyPhone;
    /**
     * 乙方联系电话
     */
    private String secondPartyPhone;
    /**
     * 合同扫描件
     */
    private String scanningCopy;
    /**
     * 项目进度
     */
    private String projectProgress;
    /**
     * 存在问题
     */
    private String existingProblem;
    /**
     * 用户ID
     */
    private Long userId;
}
