package com.xuan.pojo.entity;

import lombok.Data;

@Data
public class Customer {
    private Integer id;       // 主键
    private String name;      // 客户姓名
    private String telegram;  // 客户联系方式
}
