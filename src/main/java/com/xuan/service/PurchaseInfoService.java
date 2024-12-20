package com.xuan.service;

import com.xuan.pojo.dto.PurchaseInfoDTO;
import com.xuan.pojo.vo.PurchaseInfoVO;

import java.util.List;

public interface PurchaseInfoService {

    /**
     * 获取进货单列表
     *
     * @return 进货单列表
     */
    List<PurchaseInfoVO> getPurchaseInfoList();

    /**
     * 完成进货
     *
     * @param purchaseInfoDTO 进货单DTO
     * @return 进货处理结果
     */
    String purchase(PurchaseInfoDTO purchaseInfoDTO);
}
