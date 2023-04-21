package com.contractManagement.repository;

import com.contractManagement.entity.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRecordRepo extends JpaRepository<PaymentRecord, Long> {
    List<PaymentRecord> findAllByContractId(Long contractId);
    void deleteAllByContractId(Long contractId);
}
