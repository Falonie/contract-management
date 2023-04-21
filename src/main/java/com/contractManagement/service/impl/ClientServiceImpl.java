package com.contractManagement.service.impl;

import com.contractManagement.entity.Client;
import com.contractManagement.entity.dto.ClientRequest;
import com.contractManagement.enums.ResultEnum;
import com.contractManagement.exception.ResultException;
import com.contractManagement.repository.ClientRepo;
import com.contractManagement.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 甲方客户信息管理 serviceImpl
 */
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepo clientRepo;

    @Override
    public Client save(Client client) {
        return clientRepo.save(client);
    }

    @Override
    public Client findById(Long id) {
        return clientRepo.findById(id).orElseThrow(() -> new ResultException(ResultEnum.CLIENT_NOT_EXIST));
    }

    @Override
    public void delete(Iterable<Long> ids) {
        clientRepo.deleteAllByIdInBatch(ids);
    }

    /**
     * 查询
     * @param request ClientRequest
     * @param pageable
     * @return
     */
    @Override
    public Page<Client> findAll(ClientRequest request, Pageable pageable) {
        Specification<Client> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (null != request.getUnitName()) {
                predicates.add(criteriaBuilder.like(root.get("unitName").as(String.class), "%" + request.getUnitName() + "%"));
            }
            if (null != request.getUnitPrincipal()) {
                predicates.add(criteriaBuilder.like(root.get("unitPrincipal").as(String.class), "%" + request.getUnitPrincipal() + "%"));
            }
            if (null != request.getBusinessCategory()) {
                predicates.add(criteriaBuilder.like(root.get("businessCategory").as(String.class), "%" + request.getBusinessCategory() + "%"));
            }
            Predicate[] p = new Predicate[predicates.size()];
            return criteriaBuilder.and(predicates.toArray(p));
        };
        Page<Client> clientPage = clientRepo.findAll(specification, pageable);
        return clientPage;
    }
}
