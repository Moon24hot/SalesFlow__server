package com.xuan.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class ContractDto {
    private String customerName;
    private String salespersonName;
    private List<ProductQuantity> productList;

    @Data
    public static class ProductQuantity {
        private String productName;
        private Integer quantity;
    }
}
