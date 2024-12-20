package com.xuan.pojo.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private Integer id;             // 主键
    private String name;            // 商品名称
    private Integer currentStock;   // 当前库存
    private Integer stockThreshold; // 库存阈值
    private BigDecimal price;       // 单价
}
