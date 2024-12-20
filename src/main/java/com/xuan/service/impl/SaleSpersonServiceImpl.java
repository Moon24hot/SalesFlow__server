package com.xuan.service.impl;

import com.xuan.mapper.ContractMapper;
import com.xuan.mapper.SaleSpersonMapper;
import com.xuan.pojo.dto.SalesPerformanceDTO;
import com.xuan.pojo.dto.SalespersonDTO;
import com.xuan.pojo.entity.SaleSperson;
import com.xuan.pojo.vo.SaleSpersonVO;
import com.xuan.service.SaleSpersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleSpersonServiceImpl implements SaleSpersonService {

    @Autowired
    private SaleSpersonMapper saleSpersonMapper;

    @Autowired
    private ContractMapper contractMapper;

    @Override
    public SaleSperson findByName(String name) {
        return saleSpersonMapper.findByName(name);
    }

    @Override
    public List<SaleSpersonVO> getAllSaleSpersons() {
        // 查询所有销售人员并转换为 VO
        return saleSpersonMapper.findAllSaleSpersons().stream().map(saleSperson -> {
            SaleSpersonVO vo = new SaleSpersonVO();
            vo.setId(saleSperson.getId());
            vo.setName(saleSperson.getName());
            vo.setPassword(saleSperson.getPassword());
            vo.setTelegram(saleSperson.getTelegram());
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean addSalesperson(SalespersonDTO salespersonDTO) {
        // 创建 Salesperson 实体并保存到数据库
        SaleSperson salesperson = new SaleSperson();
        salesperson.setName(salespersonDTO.getName());
        salesperson.setPassword(salespersonDTO.getPassword());
        salesperson.setTelegram(salespersonDTO.getTelegram());

        return saleSpersonMapper.insertSalesperson(salesperson) > 0; // 返回插入结果
    }

    @Override
    public boolean updateSalesperson(SalespersonDTO salespersonDTO) {
        // 查询销售人员是否存在
        SaleSperson existingSalesperson = saleSpersonMapper.findSalespersonById(salespersonDTO.getId());
        if (existingSalesperson != null) {
            existingSalesperson.setName(salespersonDTO.getName());
            existingSalesperson.setPassword(salespersonDTO.getPassword());
            existingSalesperson.setTelegram(salespersonDTO.getTelegram());
            return saleSpersonMapper.updateSalesperson(existingSalesperson) > 0; // 返回更新结果
        }
        return false; // 如果销售人员不存在，返回 false
    }

    @Override
    public SalesPerformanceDTO getSalesPerformance(Integer salespersonId,
                                                   LocalDateTime startDate,
                                                   LocalDateTime endDate) {
// 获取销售人员的历史销售额
        SaleSperson salesperson = saleSpersonMapper.findById(salespersonId);

        // 将历史销售额从 Double 转换为 BigDecimal
        BigDecimal historicalSales = BigDecimal.valueOf(salesperson.getAmount()); // 历史销售额

        // 从合同表中查询在指定日期范围内已支付合同的总销售额和签订合同数
        BigDecimal totalSales = contractMapper.sumTotalSalesBySalespersonAndDateRange(salespersonId, startDate, endDate);
        int contractCount = contractMapper.countContractsBySalespersonAndDateRange(salespersonId, startDate, endDate);

        // 返回结果
        SalesPerformanceDTO performanceDTO = new SalesPerformanceDTO();
        performanceDTO.setTotalSales(totalSales);
        performanceDTO.setContractCount(contractCount);
        performanceDTO.setHistoricalSales(historicalSales);

        return performanceDTO;
    }
}
