package com.xuan.mapper;

import com.xuan.pojo.entity.Contract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ContractMapper {
    void insertContract(Contract contract);

    @Select("SELECT * FROM contract")
    List<Contract> findAllContracts();

    @Select("SELECT name FROM customer WHERE id = #{customerId}")
    String findCustomerNameById(Integer customerId);

    @Select("SELECT name FROM sale_sperson WHERE id = #{salespersonId}")
    String findSalespersonNameById(Integer salespersonId);

    /**
     * 根据销售人员ID查询合同列表
     *
     * @param salespersonId 销售人员ID
     * @return 合同列表
     */
    @Select("SELECT * FROM contract WHERE salesperson_id = #{salespersonId}")
    List<Contract> findContractsBySalespersonId(Integer salespersonId);

    /**
     * 根据合同ID查询支付状态
     *
     * @param contractId 合同ID
     * @return 支付状态
     */
    @Select("SELECT pay_status FROM contract WHERE id = #{contractId}")
    Integer getPayStatusById(Integer contractId);

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

    @Update("UPDATE contract SET status = #{status} WHERE id = #{contractId}")
    int updateContractStatus(@Param("contractId") Integer contractId, @Param("status") Integer status);

    @Select("SELECT * FROM contract WHERE id = #{contractId}")
    Contract findById(Integer contractId);

    /**
     * 更新合同的客户和销售人员ID
     *
     * @param contract 合同实体
     * @return 更新的记录数
     */
    @Update("UPDATE contract SET customer_id = #{customerId}, salesperson_id = #{salespersonId} WHERE id = #{id}")
    int updateContract(Contract contract);
}
