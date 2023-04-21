package com.contractManagement.controller;

import com.contractManagement.common.Result;
import com.contractManagement.entity.PaymentRecord;
import com.contractManagement.service.PaymentRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 支付记录Controller
 */
@RestController
@RequestMapping("/paymentRecord")
@RequiredArgsConstructor
public class PaymentRecordController {
    private final PaymentRecordService paymentRecordService;

    /**
     * 保存已支付金额记录
     *
     * @param paymentRecord 支付记录
     * @return
     */
    @PostMapping("/saveSubPaymentRecord")
    public Result<PaymentRecord> saveContract(@Validated @RequestBody PaymentRecord paymentRecord) {
//        val contract = buildContract(contractDto);
        PaymentRecord paymentRecord1 = paymentRecordService.save(paymentRecord);
        return Result.success("添加支付记录成功", paymentRecord1);
    }

    /**
     * 保存已支付金额记录
     *
     * @param paymentRecords paymentRecords
     * @return
     */
    @PostMapping("/saveSubPaymentRecordList")
    public Result<Iterable<PaymentRecord>> saveContract(@RequestBody List<PaymentRecord> paymentRecords) {
//        val contract = buildContract(contractDto);
        Iterable<PaymentRecord> paymentRecord1 = paymentRecordService.saveALl(paymentRecords);
        return Result.success("添加支付记录成功", paymentRecord1);
    }

    /**
     * 查找该合同关联的已支付记录
     *
     * @param contractId 合同id
     * @return
     */
    @GetMapping("findPaymentRecords/{contractId}")
    public Result<List<PaymentRecord>> findPaymentRecords(@PathVariable("contractId") Long contractId) {
        List<PaymentRecord> paymentRecords = paymentRecordService.findPaymentRecordsByContractId(contractId);
        return Result.success(paymentRecords);
    }

    /**
     * 查找已支付记录
     *
     * @param id id
     * @return
     */
    @GetMapping("findPaymentRecord/{id}")
    public Result<PaymentRecord> findPaymentRecord(@PathVariable("id") Long id) {
        PaymentRecord paymentRecord = paymentRecordService.findById(id);
        return Result.success(paymentRecord);
    }

    /**
     * 更新支付记录
     *
     * @param paymentRecord 支付记录
     * @return
     */
    @PutMapping("updatePaymentRecord")
    public Result<PaymentRecord> updatePaymentRecord(@RequestBody PaymentRecord paymentRecord) {
        PaymentRecord paymentRecord1 = paymentRecordService.findById(paymentRecord.getId());
        paymentRecord1 = buildPaymentRecord(paymentRecord);
        PaymentRecord result = paymentRecordService.save(paymentRecord1);
        return Result.success("更新成功", result);
    }

    /**
     * 删除支付记录
     * @param contractId contractId
     * @return
     */
    @DeleteMapping("deleteSubPaymentRecord/contractId/{contractId}")
/*    public Result<String> deleteSubPaymentRecord(@RequestBody Iterable<Long> ids) {
        paymentRecordService.delete(ids);
        return Result.success("删除成功");
    }*/
    public Result<String> deleteSubPaymentRecord(@PathVariable("contractId") Long contractId) {
        paymentRecordService.deleteAllByContractId(contractId);
        return Result.success("删除成功");
    }

    public PaymentRecord buildPaymentRecord(PaymentRecord paymentRecord) {
        return PaymentRecord.builder()
                .id(paymentRecord.getId())
                .subPayment(paymentRecord.getSubPayment())
                .paymentTime(paymentRecord.getPaymentTime())
                .paidAmount(paymentRecord.getPaidAmount())
                .bank(paymentRecord.getBank())
                .confirmPerson(paymentRecord.getConfirmPerson())
                .projectName(paymentRecord.getProjectName())
                .contractId(paymentRecord.getContractId())
                .build();
    }
}
