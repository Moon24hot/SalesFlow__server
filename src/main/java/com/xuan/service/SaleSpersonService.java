package com.xuan.service;

import com.xuan.pojo.dto.SalesPerformanceDTO;
import com.xuan.pojo.dto.SalespersonDTO;
import com.xuan.pojo.entity.SaleSperson;
import com.xuan.pojo.vo.SaleSpersonVO;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleSpersonService {
    SaleSperson findByName(String name);
    /**
     * 获取所有销售人员信息
     *
     * @return 销售人员列表
     */
    List<SaleSpersonVO> getAllSaleSpersons();

    /**
     * 新增销售人员
     *
     * @param salespersonDTO 销售人员信息
     * @return 是否成功
     */
    boolean addSalesperson(SalespersonDTO salespersonDTO);

    /**
     * 修改销售人员信息
     *
     * @param salespersonDTO 销售人员信息
     * @return 是否成功
     */
    boolean updateSalesperson(SalespersonDTO salespersonDTO);

    /**
     * 根据销售人员 ID 和日期范围查询销售业绩
     *
     * @param salespersonId 销售人员ID
     * @param startDate     开始日期
     * @param endDate       结束日期
     * @return 销售业绩数据
     */
    SalesPerformanceDTO getSalesPerformance
    (Integer salespersonId, LocalDateTime startDate, LocalDateTime endDate);
}
