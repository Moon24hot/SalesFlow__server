package com.xuan.service;

import com.xuan.pojo.dto.ContractUpdateDTO;
import com.xuan.pojo.entity.Contract;
import com.xuan.pojo.entity.ContractDetail;
import com.xuan.pojo.vo.ContractVO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ContractService {
    void saveContract(Contract contract);
    void saveContractDetail(ContractDetail contractDetail);
    List<ContractVO> getAllContracts();
    /**
     * 查询某销售人员的相关合同列表
     *
     * @param salespersonId 销售人员ID
     * @return 合同列表
     */
    List<ContractVO> getContractsBySalesperson(Integer salespersonId);

    /**
     * 修改合同支付状态为已支付，并更新对应销售人员的销售额
     *
     * @param contractId 合同ID
     * @return 修改结果
     *         - "success"：修改成功
     *         - "not_found"：合同不存在
     *         - "already_paid"：合同已支付
     */
    String updatePayStatusAndSalesAmount(Integer contractId);

    /**
     * 修改合同
     *
     * @param contractUpdateDTO 修改后的合同信息
     * @return 操作结果
     */
    String updateContract(ContractUpdateDTO contractUpdateDTO);

    BigDecimal calculateTotalSales(); // 查询所有已支付订单的总销售额

    BigDecimal calculateSalesInPeriod(LocalDateTime startDate, LocalDateTime endDate); // 查询指定时间段内的销售额

    List<Map<String, Object>> getCustomerSales();

    List<Map<String, Object>> getProductSales();
}
