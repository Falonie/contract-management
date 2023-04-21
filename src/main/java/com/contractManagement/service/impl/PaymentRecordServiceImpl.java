package com.contractManagement.service.impl;

import com.contractManagement.entity.PaymentRecord;
import com.contractManagement.enums.ResultEnum;
import com.contractManagement.exception.ResultException;
import com.contractManagement.repository.PaymentRecordRepo;
import com.contractManagement.service.PaymentRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 支付记录 ServiceImpl
 */
@Service
@RequiredArgsConstructor
public class PaymentRecordServiceImpl implements PaymentRecordService {
    private final PaymentRecordRepo paymentRecordRepo;

    /**
     * 保存支付记录
     * @param paymentRecord 支付记录实体
     * @return
     */
    @Override
    public PaymentRecord save(PaymentRecord paymentRecord) {
        return paymentRecordRepo.save(paymentRecord);
    }

    @Override
    public List<PaymentRecord> saveALl(Iterable<PaymentRecord> paymentRecords) {
        return paymentRecordRepo.saveAll(paymentRecords);
    }

    /**
     * 查找支付记录
     *
     * @param id id
     * @return
     */
    @Override
    public PaymentRecord findById(Long id) {
        return paymentRecordRepo.findById(id).orElseThrow(() -> new ResultException(ResultEnum.PAYMENT_RECORD_NOT_EXIST));
    }

    /**
     * 查找该合同关联的支付记录
     *
     * @param contractId 合同id
     * @return
     */
    @Override
    public List<PaymentRecord> findPaymentRecordsByContractId(Long contractId) {
        return paymentRecordRepo.findAllByContractId(contractId);
    }

    /**
     * 删除支付记录
     * @param ids ids
     */
    @Override
    public void delete(Iterable<Long> ids) {
        paymentRecordRepo.deleteAllByIdInBatch(ids);
    }

    /**
     * 删除支付记录
     * @param contractId contractId
     */
    @Override
    @Transactional
    public void deleteAllByContractId(Long contractId) {
        paymentRecordRepo.deleteAllByContractId(contractId);
    }
}
