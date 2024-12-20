package com.xuan.mapper;

import com.xuan.pojo.entity.PurchaseInfo;
import com.xuan.pojo.vo.PurchaseInfoVO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PurchaseInfoMapper {

    /**
     * 插入进货信息
     *
     * @param purchaseInfo 进货信息实体
     * @return 插入的记录数
     */
    @Insert("INSERT INTO purchase_info (product_id, quantity) VALUES (#{productId}, #{quantity})")
    int insertPurchaseInfo(PurchaseInfo purchaseInfo);

    /**
     * 查询所有进货信息及对应商品名称
     *
     * @return 进货单列表
     */
    @Select("SELECT p.id AS id, pr.name AS productName, " +
            "p.quantity AS quantity, " +
            "CASE p.status WHEN 0 THEN '未进货' WHEN 1 THEN '已进货' END AS status " +
            "FROM purchase_info p " +
            "JOIN product pr ON p.product_id = pr.id")
    List<PurchaseInfoVO> findAllPurchaseInfo();

    /**
     * 根据ID查询进货信息
     *
     * @param id 进货单ID
     * @return 进货信息
     */
    @Select("SELECT * FROM purchase_info WHERE id = #{id}")
    PurchaseInfo findById(Integer id);

    /**
     * 更新进货信息状态和进货日期
     *
     * @param purchaseInfo 进货信息实体
     * @return 更新的记录数
     */
    @Update("UPDATE purchase_info SET status = #{status}, purchase_date = #{purchaseDate} WHERE id = #{id}")
    int updatePurchaseInfo(PurchaseInfo purchaseInfo);
}
