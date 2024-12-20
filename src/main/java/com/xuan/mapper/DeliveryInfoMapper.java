package com.xuan.mapper;

import com.xuan.pojo.entity.DeliveryInfo;
import com.xuan.pojo.vo.DeliveryInfoVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DeliveryInfoMapper {

    /**
     * 插入发货信息
     *
     * @param deliveryInfo 发货信息实体
     * @return 插入成功的记录数
     */
    @Insert("INSERT INTO delivery_info (contract_id, product_id, quantity) VALUES (#{contractId}, #{productId}, #{quantity})")
    int insertDeliveryInfo(DeliveryInfo deliveryInfo);

    /**
     * 查询所有发货信息及对应商品名称
     *
     * @return 发货信息列表
     */
    @Select("SELECT d.id AS id, p.name AS productName, d.quantity AS quantity, " +
            "CASE d.status WHEN 0 THEN '未发货' WHEN 1 THEN '已发货' END AS status, " +
            "d.tracking_number AS trackingNumber " +
            "FROM delivery_info d " +
            "JOIN product p ON d.product_id = p.id")
    List<DeliveryInfoVO> findAllDeliveryInfo();

    /**
     * 根据发货单ID查询发货信息
     *
     * @param id 发货单ID
     * @return 发货信息
     */
    @Select("SELECT * FROM delivery_info WHERE id = #{id}")
    DeliveryInfo findById(Integer id);

    /**
     * 更新发货信息
     *
     * @param deliveryInfo 发货信息实体
     * @return 更新的记录数
     */
    @Update("UPDATE delivery_info SET status = #{status}, tracking_number = #{trackingNumber}, delivery_date = #{deliveryDate} WHERE id = #{id}")
    int updateDeliveryInfo(DeliveryInfo deliveryInfo);

    /**
     * 根据合同ID查询所有发货信息
     *
     * @param contractId 合同ID
     * @return 发货信息列表
     */
    @Select("SELECT * FROM delivery_info WHERE contract_id = #{contractId}")
    List<DeliveryInfo> findByContractId(Integer contractId);
}
