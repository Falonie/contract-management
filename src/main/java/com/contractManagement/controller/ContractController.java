package com.contractManagement.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.contractManagement.common.Result;
import com.contractManagement.entity.Contract;
import com.contractManagement.entity.dto.ContractDto;
import com.contractManagement.enums.ResultEnum;
import com.contractManagement.exception.ResultException;
import com.contractManagement.repository.ContractRepo;
import com.contractManagement.service.ContractService;
import com.contractManagement.service.PaymentRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 合同 controller
 */
@RestController
@RequestMapping("/contract")
@RequiredArgsConstructor
public class ContractController {
    private final ContractService contractService;
    private final PaymentRecordService paymentRecordService;
    private final ContractRepo contractRepo;

    /**
     * 保存合同
     *
     * @param contractDto
     * @return
     */
    @PostMapping("/saveContract")
    public Result<Contract> saveContract(@Validated @RequestBody ContractDto contractDto) {
        Contract contract = buildContract(contractDto);
        contract.setUserId(StpUtil.getLoginIdAsLong());
        Optional<Contract> contractOptional = contractService.findByProjectName(contractDto.getProjectName());
        if (contractOptional.isPresent()) {
            throw new ResultException(ResultEnum.CONTRACT_HAS_EXISTED);
        }
        Contract contract1 = contractService.save(contract);
        return Result.success("创建成功", contract1);
    }

    /**
     * 查找合同
     *
     * @param id contractId
     * @return
     */
    @GetMapping("findContract/id/{id}")
    public Result<Contract> findContract(@PathVariable("id") Long id) {
//        long userId2 = StpUtil.getLoginIdAsLong();
        return Result.success(contractService.findById(id));
    }

    /**
     * 搜索查询合同
     *
     * @param userId
     * @param projectName
     * @param pageSize
     * @param pageNum
     * @return
     */
    @GetMapping("findContract")
    public Result<Page<Contract>> findAll(
//            @RequestParam(value = "userId", required = false) Long userId,
//            @RequestParam(value = "projectName", defaultValue = "") String projectName,
//            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
//            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum
//            @RequestParam(value = "name", defaultValue = "") String name,
//            @RequestParam(value = "startAmount", defaultValue = "") String startAmount,
//            @RequestParam(value = "endAmount", defaultValue = "") String endAmount,
            ContractDto contractDto
    ) {
//        Page<Contract> contractPage = contractService.findAllByUserId(
//                userId, PageRequest.of(pageSize - 1, pageNum));
//                contractDto.getUserId(), PageRequest.of(contractDto.getPageSize(), contractDto.getPageNum()));
//                contractDto.getUserId(), PageRequest.of(0, 4));

        Long userId = StpUtil.getLoginIdAsLong();
/*        Page<Contract> contractPage = contractService.findAllByUserIdAndProjectNameContaining(
                userId, contractDto.getProjectName(),
                PageRequest.of(contractDto.getPageNum() - 1, contractDto.getPageSize()));*/
/*        Page<Contract> contractPage = contractService.findAllByUserId(
                userId, name, *//*startAmount, endAmount,*//*
                PageRequest.of(contractDto.getPageNum() - 1, contractDto.getPageSize()));*/
        Page<Contract> contractPage = contractService.findAll(contractDto,
                PageRequest.of(contractDto.getPageNum() - 1, contractDto.getPageSize(), Sort.Direction.DESC, "signDate"));
        return Result.success(contractPage);
    }

    @GetMapping("findContracts")
    public Result<Page<Contract>> findContracts(ContractDto contractDto) {
        Page<Contract> contractPage = contractService.findContracts(contractDto.getProjectName(),
                PageRequest.of(contractDto.getPageNum(), contractDto.getPageSize(), Sort.Direction.DESC, "signDate"));
        return Result.success(contractPage);
    }

    /**
     * 更新合同
     *
     * @param contractDto 合同请求体
     * @return
     */
    @PutMapping("updateContract")
    public Result<Contract> updateContract(@RequestBody @Validated ContractDto contractDto) {
        Contract contract = contractService.findById(contractDto.getId());
        Contract contract1 = buildContract(contractDto);
        Contract result = contractService.save(contract1);
        return Result.success("更新成功", result);
/*        Contract result = contractRepo.findById(contractDto.getId())
                .map(contract -> {
                    contract.setProjectName(contractDto.getProjectName());
                    return contractRepo.save(contract);
                })
                .orElseThrow(() -> new ResultException(ResultEnum.CONTRACT_NOT_EXIST));
        return Result.success("更新成功", result);*/
    }

    /**
     * @param ids idList
     * @return
     */
    @DeleteMapping("deleteContract")
    public Result<String> deleteContract(@RequestBody Iterable<Long> ids) {
        contractService.delete(ids);
        for (Long id : ids) {
            paymentRecordService.deleteAllByContractId(id);
        }
        return Result.success("删除成功");
    }

    /**
     * 统计分析接口
     *
     * @param contractDto contractDto
     * @return
     */
    @GetMapping("statistic")
    public Result<BigDecimal> statistic(ContractDto contractDto) {
        BigDecimal amountSum = contractService.findAllStatistic(contractDto);
        return Result.success(amountSum);
    }

    private Contract buildContract(ContractDto contractDto) {
        return Contract.builder()
                .id(contractDto.getId())
                .projectName(contractDto.getProjectName())
                .firstParty(contractDto.getFirstParty())
                .secondParty(contractDto.getSecondParty())
                .signDate(contractDto.getSignDate())
                .totalAmount(contractDto.getTotalAmount())
                .paymentType(contractDto.getPaymentType())
                .paidAmount(contractDto.getPaidAmount())
                .paymentRemark(contractDto.getPaymentRemark())
                .firstPartyContractor(contractDto.getFirstPartyContractor())
                .secondPartyContractor(contractDto.getSecondPartyContractor())
                .firstPartyPhone(contractDto.getFirstPartyPhone())
                .secondPartyPhone(contractDto.getSecondPartyPhone())
                .scanningCopy(contractDto.getScanningCopy())
                .projectProgress(contractDto.getProjectProgress())
                .existingProblem(contractDto.getExistingProblem())
                .userId(contractDto.getUserId())
                .contractCategory(contractDto.getContractCategory())
                .clientCategory(contractDto.getClientCategory())
                .build();
    }

}
