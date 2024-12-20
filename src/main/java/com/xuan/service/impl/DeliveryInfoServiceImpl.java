package com.xuan.service.impl;

import com.xuan.mapper.*;
import com.xuan.pojo.entity.*;
import com.xuan.pojo.vo.DeliveryInfoVO;
import com.xuan.service.DeliveryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeliveryInfoServiceImpl implements DeliveryInfoService {

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private ContractDetailMapper contractDetailMapper;

    @Autowired
    private DeliveryInfoMapper deliveryInfoMapper;

    @Autowired
    private PurchaseInfoMapper purchaseInfoMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public boolean generateDeliveryInfo(Integer contractId) {
        // 查找合同以确认支付状态,而且如果已经生成过发货单(合同状态不是履行前),也不可发货
        Contract contract = contractMapper.findContractById(contractId);
        if (contract == null || contract.getPayStatus() != 1 || contract.getStatus() != 1) {
            return false; // 合同不存在或未支付
        }

        // 获取该合同的所有商品详细信息
        List<ContractDetail> contractDetails = contractDetailMapper.findContractDetailsByContractId(contractId);

        // 遍历每个产品详细信息并插入发货信息表
        for (ContractDetail detail : contractDetails) {
            DeliveryInfo deliveryInfo = new DeliveryInfo();
            deliveryInfo.setContractId(contractId);
            deliveryInfo.setProductId(detail.getProductId());
            deliveryInfo.setQuantity(detail.getQuantity());

            // 插入发货信息表，其它字段（发货日期和快递编号）使用数据库默认值
            deliveryInfoMapper.insertDeliveryInfo(deliveryInfo);
        }

        // 更新合同状态为履行中
        contractMapper.updateContractStatus(contractId, 2);

        return true;
    }

    @Override
    public List<DeliveryInfoVO> getDeliveryInfoList() {
        List<DeliveryInfoVO> deliveryInfoList = deliveryInfoMapper.findAllDeliveryInfo();

        // 对发货状态和快递编号进行后处理
        for (DeliveryInfoVO deliveryInfo : deliveryInfoList) {
            // 如果快递编号为 null，则返回 "无"
            deliveryInfo.setTrackingNumber(deliveryInfo.getTrackingNumber() != null ? deliveryInfo.getTrackingNumber() : "无");
        }
        return deliveryInfoList;
    }

    /**
     * 发货
     *
     * @param id             发货单编号
     * @param trackingNumber 快递编号
     * @return
     */
    @Override
    public String deliver(Integer id, String trackingNumber) {
        // 查询发货信息
        DeliveryInfo deliveryInfo = deliveryInfoMapper.findById(id);
        if (deliveryInfo == null || deliveryInfo.getStatus() != 0) {
            return "发货失败：该发货单已发货或不存在";
        }

        // 查询商品信息
        Product product = productMapper.findById(deliveryInfo.getProductId());

        // 更新发货信息
        int newStock = product.getCurrentStock() - deliveryInfo.getQuantity();
        boolean needsPurchaseOrder = newStock < product.getStockThreshold();

        // 检查库存
        if (newStock < 0 || needsPurchaseOrder) {
            // 生成进货单
            PurchaseInfo purchaseInfo = new PurchaseInfo();
            purchaseInfo.setProductId(deliveryInfo.getProductId());
            purchaseInfo.setQuantity((product.getStockThreshold() - newStock) + (int) (0.1 * product.getStockThreshold() + 1));
            purchaseInfoMapper.insertPurchaseInfo(purchaseInfo);
            return "发货失败：库存不足，已生成相应的进货单";
        }

        // 更新商品库存
        productMapper.updateStock(deliveryInfo.getProductId(), -deliveryInfo.getQuantity());

        // 完成发货并更新发货信息
        deliveryInfo.setStatus(1); // 状态改为已发货
        deliveryInfo.setTrackingNumber(trackingNumber);
        deliveryInfo.setDeliveryDate(LocalDateTime.now());
        deliveryInfoMapper.updateDeliveryInfo(deliveryInfo);

        // 检查合同状态
        List<DeliveryInfo> allDeliveries = deliveryInfoMapper.findByContractId(deliveryInfo.getContractId());
        boolean allDelivered = allDeliveries.stream().allMatch(d -> d.getStatus() == 1);

        if (allDelivered) {
            contractMapper.updateContractStatus(deliveryInfo.getContractId(), 3); // 合同状态改为已完成
        }

        return "发货成功";
    }

}
