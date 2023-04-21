package com.contractManagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 支付记录实体类
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
//@Table(name = "PaymentRecord")
public class PaymentRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private String paymentTime;
    /**
     * 支付金额
     */
    private String subPayment;
    /**
     * 已支付金额
     */
    private String paidAmount;
    /**
     * 到款银行
     */
    private String bank;
    /**
     * 到款确认人
     */
    private String confirmPerson;
    /**
     * 合同ID
     */
    private Long contractId;

}
