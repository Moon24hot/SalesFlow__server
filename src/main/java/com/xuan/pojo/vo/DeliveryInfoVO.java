package com.xuan.pojo.vo;

import lombok.Data;

@Data
public class DeliveryInfoVO {
    private Integer id;          // 编号（发货信息表的ID）
    private String productName;   // 商品名称
    private Integer quantity;      // 发货数量
    private String status;         // 发货状态（0为未发货，1为已发货）
    private String trackingNumber; // 快递编号
}
