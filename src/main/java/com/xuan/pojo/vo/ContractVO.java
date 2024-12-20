package com.xuan.pojo.vo;

import lombok.Data;

@Data
public class ContractVO {
    private Integer id;              // 合同编号
    private String customerName;     // 客户名
    private String salespersonName;  // 销售人员名称
    private String amount;           // 合同总金额
    private String status;           // 合同状态
    private String payStatus;        // 支付状态
}
