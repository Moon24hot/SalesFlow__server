package com.xuan.controller;

import com.xuan.pojo.vo.ContractVO;
import com.xuan.result.Result;
import com.xuan.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 销售人员模块
 */
@RestController
@RequestMapping("/api/sales")
public class SaleSpersonController {

    @Autowired
    private ContractService contractService;

    /**
     * 销售人员-查询自己相关的合同列表
     * @param salespersonId 销售人员ID
     * @return 合同列表或错误信息
     */
    @GetMapping("/contract/list/bySalesperson/{id}")
    public Result<List<ContractVO>> getContractsBySalesperson(@PathVariable("id") Integer salespersonId) {
        List<ContractVO> contracts = contractService.getContractsBySalesperson(salespersonId);

        // 如果查询结果为空
        if (contracts == null || contracts.isEmpty()) {
            return Result.error("未找到销售人员ID为 [" + salespersonId + "] 的相关合同信息");
        }

        // 查询成功，返回数据
        return Result.success(contracts);
    }


    /**
     * 销售人员-修改合同支付状态
     * @param contractId 合同ID
     * @return 修改结果
     */
    @PostMapping("/contract/pay/{id}")
    public Result<?> updateContractPayStatus(@PathVariable("id") Integer contractId) {
        // 调用 Service 更新支付状态
        String result = contractService.updatePayStatusAndSalesAmount(contractId);

        // 判断结果并返回对应的响应
        if ("not_found".equals(result)) {
            return Result.error("合同ID [" + contractId + "] 不存在");
        } else if ("already_paid".equals(result)) {
            return Result.error("合同ID [" + contractId + "] 已经是已支付状态，无法再次修改");
        }

        return Result.success("合同ID [" + contractId + "] 的支付状态修改为已支付，销售额已更新");
    }
}


