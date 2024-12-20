package com.xuan.service.impl;

import com.xuan.mapper.ContractMapper;
import com.xuan.mapper.ContractDetailMapper;
import com.xuan.mapper.CustomerMapper;
import com.xuan.mapper.SaleSpersonMapper;
import com.xuan.pojo.dto.ContractUpdateDTO;
import com.xuan.pojo.entity.Contract;
import com.xuan.pojo.entity.ContractDetail;
import com.xuan.pojo.vo.ContractVO;
import com.xuan.service.ContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private ContractDetailMapper contractDetailMapper;

    @Autowired
    private SaleSpersonMapper saleSpersonMapper;

    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 新增合同
     * @param contract
     */
    @Override
    public void saveContract(Contract contract) {
        contractMapper.insertContract(contract);
    }

    /**
     * 新增合同详细
     * @param contractDetail
     */
    @Override
    public void saveContractDetail(ContractDetail contractDetail) {
        contractDetailMapper.insertContractDetail(contractDetail);
    }

    /**
     * 查询所有合同
     * @return
     */
    @Override
    public List<ContractVO> getAllContracts() {
        // 查询所有合同
        List<Contract> contracts = contractMapper.findAllContracts();

        // 转换成 VO 列表
        return contracts.stream().map(contract -> {
            ContractVO vo = new ContractVO();
            vo.setId(contract.getId());
            vo.setCustomerName(contractMapper.findCustomerNameById(contract.getCustomerId()));
            vo.setSalespersonName(contractMapper.findSalespersonNameById(contract.getSalespersonId()));
            vo.setAmount(contract.getAmount().toString());

            // 转换合同状态（避免 null）
            vo.setStatus(convertContractStatus(contract.getStatus()));

            // 转换支付状态（避免 null）
            vo.setPayStatus(convertPayStatus(contract.getPayStatus()));

            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 查询某销售人员的合同
     * @param salespersonId 销售人员ID
     * @return
     */
    @Override
    public List<ContractVO> getContractsBySalesperson(Integer salespersonId) {
        // 查询当前销售人员相关的合同
        List<Contract> contracts = contractMapper.findContractsBySalespersonId(salespersonId);

        // 转换成 VO 列表
        return contracts.stream().map(contract -> {
            ContractVO vo = new ContractVO();
            vo.setId(contract.getId());
            vo.setCustomerName(contractMapper.findCustomerNameById(contract.getCustomerId()));
            vo.setAmount(contract.getAmount().toString());

            // 转换合同状态
            vo.setStatus(convertContractStatus(contract.getStatus()));

            // 转换支付状态
            vo.setPayStatus(convertPayStatus(contract.getPayStatus()));

            return vo;
        }).collect(Collectors.toList());
    }

    private String convertContractStatus(Integer status) {
        switch (status) {
            case 1:
                return "履行前";
            case 2:
                return "履行中";
            case 3:
                return "已完成";
            default:
                return "未知状态";
        }
    }

    private String convertPayStatus(Integer payStatus) {
        return payStatus == 1 ? "已支付" : "未支付";
    }

    /**
     * 销售人员更改支付状态
     * @param contractId 合同ID
     * @return
     */
    @Override
    public String updatePayStatusAndSalesAmount(Integer contractId) {
        // 查询合同是否存在
        Contract contract = contractMapper.findContractById(contractId);
        if (contract == null) {
            return "not_found";
        }

        // 检查当前支付状态
        if (contract.getPayStatus() != null && contract.getPayStatus() == 1) {
            return "already_paid";
        }

        // 更新支付状态为已支付
        contractMapper.updatePayStatus(contractId, 1);

        // 更新销售人员的销售额
        saleSpersonMapper.updateSalesAmount(contract.getSalespersonId(), contract.getAmount());

        return "success";
    }

    @Override
    public String updateContract(ContractUpdateDTO contractUpdateDTO) {
        // 查询合同信息
        Contract contract = contractMapper.findById(contractUpdateDTO.getId());
        if (contract == null) {
            return "合同修改失败: 合同不存在";
        }

        // 检查合同状态
        if (contract.getStatus() != 1) {
            return "合同修改失败: 合同状态不允许修改";
        }

        // 根据客户姓名查询客户ID
        Integer customerId = customerMapper.findCustomerIdByName(contractUpdateDTO.getCustomerName());
        if (customerId == null) {
            return "合同修改失败: 客户不存在";
        }

        // 根据销售人员姓名查询销售人员ID
        Integer salespersonId = saleSpersonMapper.findSalespersonIdByName(contractUpdateDTO.getSalespersonName());
        if (salespersonId == null) {
            return "合同修改失败: 销售人员不存在";
        }

        // 如果合同已支付，更新销售人员的销售额
        if (contract.getPayStatus() == 1) {
            // 减去原销售人员的销售额
            saleSpersonMapper.updateSalesAmount(contract.getSalespersonId(), contract.getAmount().negate());
            // 增加新销售人员的销售额
            saleSpersonMapper.updateSalesAmount(salespersonId, contract.getAmount());
        }

        // 更新合同信息
        contract.setCustomerId(customerId);
        contract.setSalespersonId(salespersonId);
        contractMapper.updateContract(contract);

        return "合同修改成功";
    }

    @Override
    public BigDecimal calculateTotalSales() {
        return contractMapper.sumTotalSales(); // 获取所有已支付合同的销售总额
    }

    @Override
    public BigDecimal calculateSalesInPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return contractMapper.sumTotalSalesInPeriod(startDate, endDate); // 获取指定时间段内已支付合同的销售额
    }

    @Override
    public List<Map<String, Object>> getCustomerSales() {
        // 从合同表和客户表获取各客户的销售额
        return contractMapper.findCustomerSales().stream().map(row -> {
            Map<String, Object> result = new HashMap<>();
            result.put("customerName", row.get("customerName"));
            result.put("sales", row.get("totalSales"));
            return result;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getProductSales() {
        // 从合同表和商品表获取各商品的销售额
        return contractMapper.findProductSales().stream().map(row -> {
            Map<String, Object> result = new HashMap<>();
            result.put("productName", row.get("productName"));
            result.put("sales", row.get("totalSales"));
            return result;
        }).collect(Collectors.toList());
    }
}
