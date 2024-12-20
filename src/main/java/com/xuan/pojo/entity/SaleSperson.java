package com.xuan.pojo.entity;

import lombok.Data;

@Data
public class SaleSperson {
    private Integer id;       // 主键
    private String name;      // 销售人员姓名
    private String password;  // 登录密码
    private String telegram;  // 联系方式
    private Double amount;    // 销售额
}
