package com.xuan.mapper;

import com.xuan.pojo.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Select("SELECT * FROM product WHERE name = #{name}")
    Product findByName(String name);
    /**
     * 查询所有商品的库存信息
     *
     * @return 商品库存列表
     */
    @Select("SELECT * FROM product")
    List<Product> findAllProducts();

    /**
     * 查询商品信息
     *
     * @param productId 商品ID
     * @return 商品信息
     */
    @Select("SELECT * FROM product WHERE id = #{productId}")
    Product findById(Integer productId);

    /**
     * 更新商品库存
     *
     * @param productId 商品ID
     * @param quantity  变动数量（发货时为负数）
     * @return 更新的记录数
     */
    @Update("UPDATE product SET current_stock = current_stock + #{quantity} WHERE id = #{productId}")
    int updateStock(@Param("productId") Integer productId, @Param("quantity") Integer quantity);

}
