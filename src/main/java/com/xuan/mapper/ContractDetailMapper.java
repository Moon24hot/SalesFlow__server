package com.xuan.mapper;

import com.xuan.pojo.entity.ContractDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ContractDetailMapper {
    void insertContractDetail(ContractDetail contractDetail);

    /**
     * 根据合同ID查询所有合同详细信息
     *
     * @param contractId 合同ID
     * @return 合同详细列表
     */
    @Select("SELECT * FROM contract_detail WHERE contract_id = #{contractId}")
    List<ContractDetail> findContractDetailsByContractId(Integer contractId);
}
