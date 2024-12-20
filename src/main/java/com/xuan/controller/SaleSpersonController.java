package com.xuan.controller;

import com.xuan.pojo.dto.SalesPerformanceDTO;
import com.xuan.pojo.vo.ContractVO;
import com.xuan.result.Result;
import com.xuan.service.ContractService;
import com.xuan.service.SaleSpersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 销售人员模块
 */
@RestController
@RequestMapping("/api/sales")
public class SaleSpersonController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private SaleSpersonService saleSpersonService;

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

    /**
     * 销售人员-查询销售业绩
     * @param salespersonId 销售人员ID
     * @param startDate     开始日期
     * @param endDate       结束日期
     * @return 销售业绩数据
     */
    @GetMapping("/performance/{id}")
    public Result<SalesPerformanceDTO> getSalesPerformance(
            @PathVariable("id") Integer salespersonId,
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        // 将 LocalDate 转换为 LocalDateTime，时间部分默认为 00:00:00
        LocalDateTime startDateTime = startDate.atStartOfDay(); // 开始时间
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59); // 结束时间，设置为当天的最后一刻

        SalesPerformanceDTO performance = saleSpersonService.getSalesPerformance(salespersonId, startDateTime, endDateTime);
        return Result.success(performance);
    }
}


