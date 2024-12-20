package com.xuan.pojo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeliveryInfo {
    private Integer id;               // 主键
    private Integer contractId;       // 合同ID
    private Integer productId;        // 商品ID
    private Integer quantity;         // 发货数量
    private LocalDateTime deliveryDate; // 发货日期
    private String trackingNumber;    // 快递编号
    private Integer status;           // 发货状态 (默认值为0)
}
