package com.contractManagement.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 甲方客户信息管理
 */
@With
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 单位名称
     */
    private String unitName;
    /**
     * 单位地址
     */
    private String unitAddress;
    /**
     * 单位负责人
     */
    private String unitPrincipal;
    /**
     * 负责人联系电话
     */
    private String unitPrincipalPhoneNum;
    /**
     * 办公室门牌
     */
    private String officeNum;
    /**
     * 信息化负责人
     */
    private String informationPrinciple;
    /**
     * 信息化负责人联系电话
     */
    private String informationPrinciplePhoneNum;
    /**
     * 行业类别
     */
    private Integer businessCategory;
}
