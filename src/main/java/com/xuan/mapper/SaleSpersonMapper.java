package com.xuan.mapper;

import com.xuan.pojo.entity.Contract;
import com.xuan.pojo.entity.SaleSperson; // 假设您有一个 SaleSperson 实体类
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface SaleSpersonMapper {

    /**
     * 根据用户名和密码查找销售人员
     */
    @Select("SELECT * FROM sale_sperson WHERE name = #{username} AND password = #{password}")
    SaleSperson findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 根据姓名查找销售人员
     */
    @Select("SELECT * FROM sale_sperson WHERE name = #{name}")
    SaleSperson findByName(String name);

    /**
     * 根据 ID 查找销售人员
     */
    @Select("SELECT * FROM sale_sperson WHERE id = #{id}")
    SaleSperson findById(@Param("id") Integer id);

    /**
     * 获取所有销售人员
     */
    @Select("SELECT * FROM sale_sperson")
    List<SaleSperson> findAll();

    /**
     * 根据合同ID查询合同信息
     *
     * @param contractId 合同ID
     * @return 合同实体
     */
    @Select("SELECT * FROM contract WHERE id = #{contractId}")
    Contract findContractById(Integer contractId);

    /**
     * 更新合同的支付状态
     *
     * @param contractId 合同ID
     * @param payStatus 新的支付状态
     */
    @Update("UPDATE contract SET pay_status = #{payStatus} WHERE id = #{contractId}")
    void updatePayStatus(@Param("contractId") Integer contractId, @Param("payStatus") Integer payStatus);

//    /**
//     * 更新销售人员的销售额
//     *
//     * @param salespersonId 销售人员ID
//     * @param amount 增加的销售额
//     */
//    @Update("UPDATE sale_sperson SET amount = amount + #{amount} WHERE id = #{salespersonId}")
//    void updateSalesAmount(@Param("salespersonId") Integer salespersonId, @Param("amount") BigDecimal amount);

    /**
     * 查询所有销售人员
     *
     * @return 销售人员列表
     */
    @Select("SELECT * FROM sale_sperson")
    List<SaleSperson> findAllSaleSpersons();

    /**
     * 新增销售人员
     *
     * @param salesperson 销售人员实体
     * @return 插入的记录数
     */
    @Insert("INSERT INTO sale_sperson (name, password, telegram) VALUES (#{name}, #{password}, #{telegram})")
    int insertSalesperson(SaleSperson salesperson);

    /**
     * 查询销售人员根据 ID
     *
     * @param id 销售人员ID
     * @return 销售人员实体
     */
    @Select("SELECT * FROM sale_sperson WHERE id = #{id}")
    SaleSperson findSalespersonById(Integer id);

    /**
     * 修改销售人员信息
     *
     * @param salesperson 销售人员实体
     * @return 更新的记录数
     */
    @Update("UPDATE sale_sperson SET name = #{name}, password = #{password}, telegram = #{telegram} WHERE id = #{id}")
    int updateSalesperson(SaleSperson salesperson);

    /**
     * 根据姓名查询销售人员ID
     *
     * @param name 销售人员姓名
     * @return 销售人员ID
     */
    @Select("SELECT id FROM sale_sperson WHERE name = #{name}")
    Integer findSalespersonIdByName(String name);

    /**
     * 更新销售人员的销售额
     *
     * @param salespersonId 销售人员ID
     * @param amount        变动的销售额
     * @return 更新的记录数
     */
    @Update("UPDATE sale_sperson SET amount = amount + #{amount} WHERE id = #{salespersonId}")
    int updateSalesAmount(@Param("salespersonId") Integer salespersonId, @Param("amount") BigDecimal amount);
}
