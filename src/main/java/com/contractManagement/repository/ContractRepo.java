package com.contractManagement.repository;

import com.contractManagement.entity.Client;
import com.contractManagement.entity.Contract;
import com.contractManagement.entity.dto.ContractDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContractRepo extends JpaRepository<Contract, Long>, JpaSpecificationExecutor<Contract> {
    Page<Contract> findAll(Pageable pageable);

    //    Page<Client> findAll(ContractDto contractDto);
//    List<Contract> findAll(ContractDto contractDto);

//    List<Contract> findAll(Specification<Contract> specification, ContractDto contractDto);

//    Page<Contract> findAll(ContractDto contractDto, Pageable pageable);

    Page<Contract> findAllByUserId(Long userId, Pageable pageable);

    Optional<Contract> findByProjectName(String projectName);

    Page<Contract> findByProjectNameContaining(String projectName, Pageable pageable);

    Page<Contract> findAllByUserIdAndProjectNameContainingOrderByIdDesc(Long userId, String projectName, Pageable pageable);

    /*    @Query(value = "select * from contract where user_id = :userId and project_name like %:name% " +
                "or contract_category like %:name% or client_category like %:name% or first_party like %:name% " +
                "or first_party_contractor like %:name% or (total_amount >= :startAmount + 0 and " +
                "total_amount <= :endAmount + 0)order by id desc", nativeQuery = true)*/
    @Query(value = "select * from contract where user_id = :userId and project_name like %:name% " +
            "or contract_category like %:name% or client_category like %:name% or first_party like %:name% " +
            "or first_party_contractor like %:name% order by id desc", nativeQuery = true)
    Page<Contract> findAllByUserId(@Param("userId") Long userId, @Param("name") String name,
//                                   @Param("startAmount") String startAmount, @Param("endAmount") String endAmount,
                                   Pageable pageable);
}
