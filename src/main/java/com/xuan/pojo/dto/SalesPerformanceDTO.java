package com.xuan.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalesPerformanceDTO {
    private BigDecimal totalSales; // 总销售额
    private Integer contractCount;  // 签订合同总数
    private BigDecimal historicalSales; // 历史销售额
}
