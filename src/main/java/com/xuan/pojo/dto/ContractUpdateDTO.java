package com.xuan.pojo.dto;

import lombok.Data;

@Data
public class ContractUpdateDTO {
    private Integer id;          // 合同ID
    private String customerName; // 修改后的客户姓名
    private String salespersonName; // 修改后的销售人员姓名
}