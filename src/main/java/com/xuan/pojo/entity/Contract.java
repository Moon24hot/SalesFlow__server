package com.xuan.pojo.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Contract {
    private Integer id;             // 主键
    private Integer customerId;     // 客户ID
    private Integer salespersonId;  // 销售人员ID
    private Integer status;         // 合同状态 (默认值为1)
    private LocalDateTime signDate; // 签订日期
    private BigDecimal amount;      // 合同总金额
    private Integer payStatus;      // 支付状态 (默认值为0)
}
