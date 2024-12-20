package com.xuan.pojo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PurchaseInfo {
    private Integer id;            // 主键
    private Integer productId;     // 商品ID
    private Integer quantity;      // 进货数量
    private LocalDateTime purchaseDate; // 进货日期
    private Integer status;        // 进货状态 (默认值为0)
}
