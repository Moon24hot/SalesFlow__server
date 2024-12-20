package com.xuan.pojo.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContractDetail {
    private Integer id;         // 主键
    private Integer contractId; // 合同ID
    private Integer productId;  // 商品ID
    private Integer quantity;   // 商品数量
    private BigDecimal price;   // 单价
}
