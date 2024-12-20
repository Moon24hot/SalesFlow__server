package com.xuan.pojo.vo;

import lombok.Data;

@Data
public class PurchaseInfoVO {
    private Integer id;          // 编号（进货单ID）
    private String productName;   // 商品名称
    private Integer quantity;      // 商品数量
    private String status;         // 进货状态（未进货或已进货）
}
