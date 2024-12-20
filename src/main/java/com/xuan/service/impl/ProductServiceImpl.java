package com.xuan.service.impl;

import com.xuan.mapper.ProductMapper;
import com.xuan.pojo.entity.Product;
import com.xuan.pojo.vo.ProductVO;
import com.xuan.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product findByName(String name) {
        return productMapper.findByName(name);
    }

    /**
     * 查询所有商品
     * @return
     */
    @Override
    public List<ProductVO> getAllProducts() {
        // 查询所有商品并转换为 VO
        return productMapper.findAllProducts().stream().map(product -> {
            ProductVO vo = new ProductVO();
            vo.setId(product.getId());
            vo.setName(product.getName());
            vo.setPrice(product.getPrice());
            vo.setCurrentStock(product.getCurrentStock());
            vo.setStockThreshold(product.getStockThreshold());
            return vo;
        }).collect(Collectors.toList());
    }
}
