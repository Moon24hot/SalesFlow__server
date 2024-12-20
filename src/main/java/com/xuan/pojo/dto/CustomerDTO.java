package com.xuan.pojo.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Integer id;      // 用于更新客户信息时传递 ID
    private String name;     // 客户名
    private String telegram;  // 联系方式
}
