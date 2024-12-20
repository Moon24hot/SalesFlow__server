package com.xuan.service.impl;

import com.xuan.mapper.ProductMapper;
import com.xuan.mapper.PurchaseInfoMapper;
import com.xuan.pojo.dto.PurchaseInfoDTO;
import com.xuan.pojo.entity.Product;
import com.xuan.pojo.entity.PurchaseInfo;
import com.xuan.pojo.vo.PurchaseInfoVO;
import com.xuan.service.PurchaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseInfoServiceImpl implements PurchaseInfoService {

    @Autowired
    private PurchaseInfoMapper purchaseInfoMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<PurchaseInfoVO> getPurchaseInfoList() {
        List<PurchaseInfoVO> purchaseInfoList = purchaseInfoMapper.findAllPurchaseInfo();
        
        return purchaseInfoList;
    }

    @Override
    public String purchase(PurchaseInfoDTO purchaseInfoDTO) {
        // 查询进货信息
        PurchaseInfo purchaseInfo = purchaseInfoMapper.findById(purchaseInfoDTO.getId());
        if (purchaseInfo == null || purchaseInfo.getStatus() != 0) {
            return "进货失败：该进货单已进货或不存在";
        }

        // 更新进货单状态和进货日期
        purchaseInfo.setStatus(1); // 状态改为已进货
        purchaseInfo.setPurchaseDate(LocalDateTime.now());
        purchaseInfoMapper.updatePurchaseInfo(purchaseInfo);

        // 更新商品库存
        Product product = productMapper.findById(purchaseInfo.getProductId());
        productMapper.updateStock(product.getId(), purchaseInfo.getQuantity());

        return "进货成功";
    }
}
