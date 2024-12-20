package com.xuan.service;

import com.xuan.pojo.entity.Product;
import com.xuan.pojo.vo.ProductVO;

import java.util.List;

public interface ProductService {
    Product findByName(String name);
    /**
     * 获取所有商品的库存信息
     *
     * @return 商品库存列表
     */
    List<ProductVO> getAllProducts();
}
