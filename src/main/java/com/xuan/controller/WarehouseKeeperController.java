package com.xuan.controller;

import com.xuan.pojo.dto.PurchaseInfoDTO;
import com.xuan.pojo.vo.DeliveryInfoVO;
import com.xuan.pojo.vo.ProductVO;
import com.xuan.pojo.vo.PurchaseInfoVO;
import com.xuan.result.Result;
import com.xuan.service.DeliveryInfoService;
import com.xuan.service.ProductService;
import com.xuan.service.PurchaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * 仓库管理员模块
 */
@RestController
@RequestMapping("/api/ware")
public class WarehouseKeeperController {

    @Autowired
    private ProductService productService;

    @Autowired
    private DeliveryInfoService deliveryInfoService;

    /**
     * 查询库存列表
     * @return 库存列表
     */
    @GetMapping("/inventory/list")
    public Result<List<ProductVO>> getInventoryList() {
        List<ProductVO> productList = productService.getAllProducts();
        return Result.success(productList);
    }

    /**
     * 获取发货单列表
     * @return 发货单列表
     */
    @GetMapping("/delivery/list")
    public Result<List<DeliveryInfoVO>> getDeliveryInfoList() {
        List<DeliveryInfoVO> deliveryInfoList = deliveryInfoService.getDeliveryInfoList();
        return Result.success(deliveryInfoList);
    }

    /**
     * 仓库管理员发货
     * @param id 发货单编号
     * @param trackingNumber 快递编号
     * @return 返回执行结果
     */
    @PostMapping("/deliver")
    public Result<String> deliver(@RequestParam Integer id, @RequestParam String trackingNumber) {
        String result = deliveryInfoService.deliver(id, trackingNumber);
        if (result.startsWith("发货失败")) {
            return Result.error(result); // 错误的发货结果
        }
        return Result.success(result); // 成功的发货结果
    }

    @Autowired
    private PurchaseInfoService purchaseInfoService;

    /**
     * 获取进货单列表
     * @return 进货单列表
     */
    @GetMapping("/purchase/list")
    public Result<List<PurchaseInfoVO>> getPurchaseInfoList() {
        List<PurchaseInfoVO> purchaseInfoList = purchaseInfoService.getPurchaseInfoList();
        return Result.success(purchaseInfoList);
    }

    /**
     * 进货接口
     * @param purchaseInfoDTO 进货单DTO
     * @return 返回执行结果
     */
    @PostMapping("/purchase")
    public Result<String> purchase(@RequestBody PurchaseInfoDTO purchaseInfoDTO) {
        String result = purchaseInfoService.purchase(purchaseInfoDTO);
        if (result.startsWith("进货失败")) {
            return Result.error(result); // 错误的进货结果
        }
        return Result.success(result); // 成功的进货结果
    }
}

