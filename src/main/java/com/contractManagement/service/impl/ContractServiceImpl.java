package com.contractManagement.service.impl;

import com.contractManagement.entity.Contract;
import com.contractManagement.entity.dto.ContractDto;
import com.contractManagement.enums.ClientCategoryEnum;
import com.contractManagement.enums.ContractCategoryEnum;
import com.contractManagement.enums.ProgressRangeEnum;
import com.contractManagement.enums.ResultEnum;
import com.contractManagement.exception.ResultException;
import com.contractManagement.repository.ContractRepo;
import com.contractManagement.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 合同 ServiceImpl
 */
@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {
    private final ContractRepo contractRepo;

    @Override
    public Contract save(Contract contract) {
        return contractRepo.save(contract);
    }

    @Override
    public Contract findById(Long id) {
        return contractRepo.findById(id).orElseThrow(() -> new ResultException(ResultEnum.CONTRACT_NOT_EXIST));
    }

    @Override
    public Optional<Contract> findByProjectName(String projectName) {
        return contractRepo.findByProjectName(projectName);
    }

    @Override
    public Page<Contract> findContracts(String projectName, Pageable pageable) {
        Page<Contract> contractPage = contractRepo.findByProjectNameContaining(projectName, pageable);
        return new PageImpl<>(contractPage.getContent());
    }

    @Override
    public Page<Contract> findAll(Pageable pageable) {
        Page<Contract> contractPage = contractRepo.findAll(pageable);
        return new PageImpl<>(contractPage.getContent());
    }

    @Override
    public Page<Contract> findAllByUserId(Long userId, Pageable pageable) {
        Page<Contract> contractPage = contractRepo.findAllByUserId(userId, pageable);
        return new PageImpl<>(contractPage.getContent());
    }

    @Override
    public Page<Contract> findAllByUserIdAndProjectNameContaining(Long userId, String projectName, Pageable pageable) {
        return contractRepo.findAllByUserIdAndProjectNameContainingOrderByIdDesc(userId, projectName, pageable);
    }

    @Override
    public Page<Contract> findAllByUserId(Long userId, String name/*, String startAmount, String endAmount*/, Pageable pageable) {
        Page<Contract> contractPage = contractRepo.findAllByUserId(userId, name/*, startAmount, endAmount*/, pageable);
        return contractPage;
    }

    /**
     * 按给定条件查询
     *
     * @param contractDto contractDto 搜索条件
     * @param pageable    pageable
     * @return
     */
    @Override
    public Page<Contract> findAll(ContractDto contractDto, Pageable pageable) {
        Page<Contract> contractPage = contractRepo.findAll(querySpecification(contractDto), pageable);
        return contractPage;
    }

    /**
     * 删除
     *
     * @param ids ids
     */
    @Override
    public void delete(Iterable<Long> ids) {
        contractRepo.deleteAllByIdInBatch(ids);
    }

    /**
     * 按客户类别统计收入信息
     *
     * @param contractDto contractDto
     * @return
     */
    @Override
    public BigDecimal findAllStatistic(ContractDto contractDto) {
        List<Contract> contractList = contractRepo.findAll(querySpecification(contractDto));
        BigDecimal amountSum = contractList.parallelStream()
//                .filter(contract -> !contract.getTotalAmount().equals(""))
                .filter(contract -> StringUtils.hasText(contract.getTotalAmount()))
                .map(contract -> new BigDecimal(contract.getTotalAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return amountSum;
    }

    /**
     * 自定义搜索条件
     *
     * @param contractDto contractDto
     * @return
     */
    private Specification<Contract> querySpecification(ContractDto contractDto) {
        Specification<Contract> specification = (root, query, criteriaBuilder) -> {
            DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter dt2 = DateTimeFormatter.ofPattern("yyyy-MM");
            Expression<String> signDate = root.get("signDate").as(String.class);
//            LocalDate localDate = LocalDate.parse(contractDto.getSignDate(), dt);
//            YearMonth yearMonth = YearMonth.of(localDate.getYear(), localDate.getMonth());
//            localDate.get
            String signDateString = signDate.toString();
//            LocalDate localDate2 = LocalDate.parse(signDate.toString(), dt);
            DateTimeFormatter dt4 = new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM")
                    .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                    .toFormatter();
//            LocalDate ld4 = LocalDate.parse("2022-05", dt4);
//            LocalDate ld5 = LocalDate.parse(contractDto.getSignDate(), dt4);
            Expression<LocalDate> signDate2 = root.get("signDate").as(LocalDate.class);
//            String signDateString = signDate2.toString();

            List<Predicate> predicates = new ArrayList<>();
            if (null != contractDto.getProjectName()) {
                predicates.add(criteriaBuilder.like(root.get("projectName").as(String.class), "%" + contractDto.getProjectName() + "%"));
            }
            if (null != contractDto.getFirstParty()) {
                predicates.add(criteriaBuilder.like(root.get("firstParty").as(String.class), "%" + contractDto.getFirstParty() + "%"));
            }
            if (null != contractDto.getFirstPartyContractor()) {
                predicates.add(criteriaBuilder.like(root.get("firstPartyContractor").as(String.class), "%" + contractDto.getFirstPartyContractor() + "%"));
            }
            if (null != contractDto.getContractCategory()) {
                predicates.add(criteriaBuilder.equal(root.get("contractCategory").as(Integer.class), contractDto.getContractCategory()));
            }
            if (null != contractDto.getClientCategory()) {
                predicates.add(criteriaBuilder.equal(root.get("clientCategory").as(Integer.class), contractDto.getClientCategory()));
            }
            if (null != contractDto.getStartTotalAmount()) {
                predicates.add(criteriaBuilder.ge(root.get("totalAmount").as(Integer.class), Integer.parseInt(contractDto.getStartTotalAmount())));
            }
            if (null != contractDto.getSignDate()) {
//                ParameterExpression<Integer> month = criteriaBuilder.parameter(Integer.class);
//                month = 1;
//                month = Integer.parseInt(contractDto.getSignDate());
//                LocalDate localDate = LocalDate.parse(contractDto.getSignDate(), dt);
//                LocalDate localDate2 = LocalDate.parse(signDate.toString(), dt);
//                Expression<String> stringExpression = localDate2.format(dt2);
//                predicates.add(criteriaBuilder.equal(root.get("signDate").as(String.class), contractDto.getSignDate()));
                predicates.add(criteriaBuilder.like(root.get("signDate").as(String.class), "%" + contractDto.getSignDate() + "%"));
//                predicates.add(criteriaBuilder.equal(criteriaBuilder.function("MONTH", Integer.class, root.get("signDate")), 11));
            }
            if (null != contractDto.getStartProgress() && null != contractDto.getEndProgress()) {
                predicates.add(criteriaBuilder.between(root.get("projectProgress").as(Integer.class),
                        contractDto.getStartProgress(), contractDto.getEndProgress()));
            }
            if (null != contractDto.getProgressRange()) {
                for (ProgressRangeEnum enumConstant : ProgressRangeEnum.class.getEnumConstants()) {
                    if (contractDto.getProgressRange().equals(enumConstant.getCode())) {
                        predicates.add(criteriaBuilder.between(root.get("projectProgress").as(Integer.class),
                                enumConstant.getStart(), enumConstant.getEnd()));
                    }
                }
/*                Arrays.stream(ProgressRangeEnum.class.getEnumConstants())
                        .filter(progressRangeEnum -> progressRangeEnum.getCode().equals(contractDto.getProgressRange()))
                        .findFirst()
                        .map(progressRangeEnum -> predicates.add(
                                criteriaBuilder.between(root.get("projectProgress").as(Integer.class),
                                        progressRangeEnum.getStart(), progressRangeEnum.getEnd())))
                        .orElseThrow(() -> new RuntimeException(""));*/
            }
            Predicate[] p = new Predicate[predicates.size()];
            return criteriaBuilder.and(predicates.toArray(p));
        };
        return specification;
    }
}
