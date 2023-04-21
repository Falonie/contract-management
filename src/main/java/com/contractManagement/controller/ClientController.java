package com.contractManagement.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.contractManagement.common.Result;
import com.contractManagement.entity.Client;
import com.contractManagement.entity.Contract;
import com.contractManagement.entity.dto.ClientRequest;
import com.contractManagement.entity.dto.ContractDto;
import com.contractManagement.enums.ResultEnum;
import com.contractManagement.exception.ResultException;
import com.contractManagement.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * 方客户信息管理 controller
 */
@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/saveClient")
    public Result<Client> saveClient(@Validated @RequestBody ClientRequest request) {
        Client client = buildClient(request);
/*        contract.setUserId(StpUtil.getLoginIdAsLong());
        Optional<Contract> contractOptional = clientService.findByProjectName(contractDto.getProjectName());
        if (contractOptional.isPresent()) {
            throw new ResultException(ResultEnum.CONTRACT_HAS_EXISTED);
        }*/
        Client clientResult = clientService.save(client);
        return Result.success("创建成功", clientResult);
    }

    /**
     * 查找甲方客户信息
     *
     * @param id id
     * @return
     */
    @GetMapping("findClient/id/{id}")
    public Result<Client> findClient(@PathVariable("id") Long id) {
        long userId = StpUtil.getLoginIdAsLong();
        return Result.success(clientService.findById(id));
    }

    /**
     * @param request ClientRequest
     * @return
     */
    @GetMapping("findAll")
    public Result<Page<Client>> findClient(ClientRequest request) {
        Page<Client> clientPage = clientService.findAll(request,
                PageRequest.of(request.getPageNum() - 1, request.getPageSize()));
        return Result.success(clientPage);
    }

    @PutMapping("updateContract")
    public Result<Client> updateClient(@RequestBody ClientRequest clientRequest) {
        Client client = clientService.findById(clientRequest.getId());
        Client client1 = buildClient(clientRequest);
        Client result = clientService.save(client1);
        return Result.success("更新成功", result);
    }

    /**
     * 删除客户
     *
     * @param ids ids
     * @return
     */
    @DeleteMapping("deleteClient")
    public Result<String> deleteClient(@RequestBody Iterable<Long> ids) {
        clientService.delete(ids);
        return Result.success("删除成功");
    }

    private Client buildClient(ClientRequest request) {
        return Client.builder()
                .id(request.getId())
                .unitName(request.getUnitName())
                .unitAddress(request.getUnitAddress())
                .unitPrincipal(request.getUnitPrincipal())
                .unitPrincipalPhoneNum(request.getUnitPrincipalPhoneNum())
                .officeNum(request.getOfficeNum())
                .informationPrinciple(request.getInformationPrinciple())
                .informationPrinciplePhoneNum(request.getInformationPrinciplePhoneNum())
                .businessCategory(request.getBusinessCategory())
                .build();
    }
}
