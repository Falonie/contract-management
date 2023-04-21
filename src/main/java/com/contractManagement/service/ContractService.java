package com.contractManagement.service;

import com.contractManagement.entity.Contract;
import com.contractManagement.entity.dto.ContractDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ContractService {
    Contract save(Contract contract);

    Contract findById(Long id);

    Optional<Contract> findByProjectName(String projectName);

    Page<Contract> findAll(Pageable pageable);

    Page<Contract> findAllByUserId(Long userId, Pageable pageable);

    Page<Contract> findContracts(String projectName, Pageable pageable);

    Page<Contract> findAllByUserIdAndProjectNameContaining(Long userId, String projectName, Pageable pageable);

    Page<Contract> findAllByUserId(Long userId, String name/*, String startAmount, String endAmount*/, Pageable pageable);

    Page<Contract> findAll(ContractDto contractDto, Pageable pageable);

    void delete(Iterable<Long> ids);

    BigDecimal findAllStatistic(ContractDto contractDto);
}
