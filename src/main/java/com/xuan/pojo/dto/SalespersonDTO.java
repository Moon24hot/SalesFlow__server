package com.xuan.pojo.dto;

import lombok.Data;

@Data
public class SalespersonDTO {
    private Integer id;        // 修改销售人员信息时传递 ID
    private String name;       // 姓名
    private String password;   // 密码
    private String telegram;    // 联系方式
}
