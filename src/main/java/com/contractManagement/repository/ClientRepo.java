package com.contractManagement.repository;

import com.contractManagement.entity.Client;
import com.contractManagement.entity.dto.ClientRequest;
import com.contractManagement.entity.dto.ContractDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClientRepo extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
    Page<Client> findAll(Pageable pageable);
}
