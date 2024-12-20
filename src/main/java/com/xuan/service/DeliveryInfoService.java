package com.xuan.service;

import com.xuan.pojo.vo.DeliveryInfoVO;

import java.util.List;

public interface DeliveryInfoService {
    /**
     * 生成发货单
     *
     * @param contractId 合同ID
     * @return 是否成功
     */
    boolean generateDeliveryInfo(Integer contractId);

    /**
     * 获取发货单列表
     *
     * @return 发货单列表
     */
    List<DeliveryInfoVO> getDeliveryInfoList();

    /**
     * 发货
     *
     * @param id 发货单编号
     * @param trackingNumber 快递编号
     * @return 发货结果信息
     */
    String deliver(Integer id, String trackingNumber);
}
