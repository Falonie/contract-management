package com.contractManagement.service;

import com.contractManagement.entity.Client;
import com.contractManagement.entity.dto.ClientRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {
    Client save(Client client);

    Client findById(Long id);

    void delete(Iterable<Long> ids);

    Page<Client> findAll(ClientRequest request, Pageable pageable);
}
