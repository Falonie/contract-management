package com.contractManagement.service.impl;

import com.contractManagement.entity.Contract;
import com.contractManagement.service.ContractService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ContractServiceImplTest {
    @Resource
    private ContractService contractService;

    @Test
    @Transactional
    void save() {
        Contract contract = Contract.builder()
                .projectName("project1").firstParty("party1").secondParty("party2").build();
        Contract contract1 = contractService.save(contract);
        assertNotNull(contract1);
    }

    @Test
    void findById() {
    }

    @Test
    void findContracts() {
    }

    @Test
    void findAll() {
    }

    @Test
    void testFindAll() {
    }
}