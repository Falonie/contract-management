package com.contractManagement.utils;

import com.contractManagement.entity.Contract;
import org.springframework.data.jpa.domain.Specification;

/**
 * 自定义查询条件
 */
public class CustomerSpecifications {
    public static Specification<Contract> projectNameLike(String projectName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("projectName").as(String.class), "%" + projectName + "%");
    }

    public static Specification<Contract> FirstPartyLike(String firstParty) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("firstParty").as(String.class), "%" + firstParty + "%");
    }

    public static Specification<Contract> ContractCategoryLike(String contractCategory) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("contractCategory").as(String.class), "%" + contractCategory + "%");
    }
}
