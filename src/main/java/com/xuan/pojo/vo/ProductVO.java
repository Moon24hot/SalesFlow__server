package com.xuan.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductVO {
    private Integer id;             // 编号
    private String name;            // 商品名称
    private BigDecimal price;       // 单价
    private Integer currentStock;   // 当前库存
    private Integer stockThreshold; // 最低库存阈值
}
