package com.xuan.pojo.dto;

import lombok.Data;

@Data
public class DeliveryInfoDTO {
    private Integer id;          // 发货单编号
    private Integer productId;   // 商品ID
    private Integer quantity;     // 发货数量
    private Integer status;       // 发货状态
    private String trackingNumber; // 快递编号
}
