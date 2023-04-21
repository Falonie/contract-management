package com.contractManagement.service;

import com.contractManagement.entity.PaymentRecord;

import java.util.List;

/**
 * 支付记录 service
 */
public interface PaymentRecordService {
    PaymentRecord save(PaymentRecord paymentRecord);

    PaymentRecord findById(Long id);

    List<PaymentRecord> findPaymentRecordsByContractId(Long contractId);

    void delete(Iterable<Long> ids);

    void deleteAllByContractId(Long contractId);

    List<PaymentRecord> saveALl(Iterable<PaymentRecord> paymentRecords);
}
