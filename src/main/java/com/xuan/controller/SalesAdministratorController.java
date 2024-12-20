package com.xuan.controller;

import com.xuan.pojo.dto.ContractUpdateDTO;
import com.xuan.pojo.dto.CustomerDTO;
import com.xuan.pojo.dto.SalespersonDTO;
import com.xuan.pojo.entity.*;
import com.xuan.pojo.dto.ContractDto;
import com.xuan.pojo.vo.ContractVO;
import com.xuan.pojo.vo.CustomerVO;
import com.xuan.pojo.vo.DeliveryInfoVO;
import com.xuan.pojo.vo.SaleSpersonVO;
import com.xuan.result.Result;
import com.xuan.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class SalesAdministratorController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SaleSpersonService saleSpersonService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private SaleSpersonService salespersonService;

    @Autowired
    private DeliveryInfoService deliveryInfoService;

    /**
     * 销售管理员-新增合同
     * @param contractDto
     * @return
     */
    @PostMapping("/contract/add")
    public Result<?> addContract(@RequestBody ContractDto contractDto) {
        // 获取输入参数
        String customerName = contractDto.getCustomerName();
        String salespersonName = contractDto.getSalespersonName();
        List<ContractDto.ProductQuantity> productList = contractDto.getProductList();

        // 存储错误信息
        Map<String, String> errors = new HashMap<>();

        // 验证客户
        Customer customer = customerService.findByName(customerName);
        if (customer == null) {
            errors.put("customer", "客户名 [" + customerName + "] 不存在");
        }

        // 验证销售人员
        SaleSperson salesperson = saleSpersonService.findByName(salespersonName);
        if (salesperson == null) {
            errors.put("salesperson", "销售人员 [" + salespersonName + "] 不存在");
        }

        // 验证商品列表
        if (productList == null || productList.isEmpty()) {
            errors.put("product", "商品列表不能为空");
        } else {
            // 验证商品
            for (ContractDto.ProductQuantity item : productList) {
                Product product = productService.findByName(item.getProductName());
                if (product == null) {
                    errors.put("product", "商品名 [" + item.getProductName() + "] 不存在");
                }
            }
        }

        // 如果有错误，返回错误信息
        if (!errors.isEmpty()) {
            return Result.error("数据校验失败: " + errors.toString());
        }

        // 校验通过后，计算合同总金额并插入数据
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (ContractDto.ProductQuantity item : productList) {
            Product product = productService.findByName(item.getProductName());
            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        // 插入合同表
        Contract contract = new Contract();
        contract.setCustomerId(customer.getId());
        contract.setSalespersonId(salesperson.getId());
        contract.setAmount(totalAmount);
        contract.setSignDate(LocalDateTime.now());
        contractService.saveContract(contract);

        // 插入合同详情表
        for (ContractDto.ProductQuantity item : productList) {
            Product product = productService.findByName(item.getProductName());
            ContractDetail detail = new ContractDetail();
            detail.setContractId(contract.getId());
            detail.setProductId(product.getId());
            detail.setQuantity(item.getQuantity());
            detail.setPrice(product.getPrice());
            contractService.saveContractDetail(detail);
        }

        return Result.success("合同新增成功");
    }

    /**
     * 销售管理员-查询合同
     * @return
     */
    @GetMapping("/contract/list")
    public Result<List<ContractVO>> getAllContracts() {
        List<ContractVO> contractList = contractService.getAllContracts();
        return Result.success(contractList);
    }

    /**
     * 查询客户列表
     * @return 客户列表
     */
    @GetMapping("/customer/list")
    public Result<List<CustomerVO>> getCustomerList() {
        List<CustomerVO> customerList = customerService.getAllCustomers();
        return Result.success(customerList);
    }

    /**
     * 查询销售人员列表
     * @return 销售人员列表
     */
    @GetMapping("/salesperson/list")
    public Result<List<SaleSpersonVO>> getSaleSpersonList() {
        List<SaleSpersonVO> saleSpersonList = saleSpersonService.getAllSaleSpersons();
        return Result.success(saleSpersonList);
    }

    /**
     * 新增客户
     * @param customerDTO 客户信息
     * @return 新增结果
     */
    @PostMapping("/customer/add")
    public Result<String> addCustomer(@RequestBody CustomerDTO customerDTO) {
        boolean success = customerService.addCustomer(customerDTO);
        if (success) {
            return Result.success("客户新增成功");
        } else {
            return Result.error("客户新增失败");
        }
    }

    /**
     * 修改客户信息
     * @param customerDTO 客户信息
     * @return 修改结果
     */
    @PostMapping("/customer/update")
    public Result<String> updateCustomer(@RequestBody CustomerDTO customerDTO) {
        boolean success = customerService.updateCustomer(customerDTO);
        if (success) {
            return Result.success("客户信息修改成功");
        } else {
            return Result.error("客户信息修改失败");
        }
    }

    /**
     * 新增销售人员
     * @param salespersonDTO 销售人员信息
     * @return 新增结果
     */
    @PostMapping("/salesperson/add")
    public Result<String> addSalesperson(@RequestBody SalespersonDTO salespersonDTO) {
        boolean success = salespersonService.addSalesperson(salespersonDTO);
        if (success) {
            return Result.success("销售人员新增成功");
        } else {
            return Result.error("销售人员新增失败");
        }
    }

    /**
     * 修改销售人员信息
     * @param salespersonDTO 销售人员信息
     * @return 修改结果
     */
    @PostMapping("/salesperson/update")
    public Result<String> updateSalesperson(@RequestBody SalespersonDTO salespersonDTO) {
        boolean success = salespersonService.updateSalesperson(salespersonDTO);
        if (success) {
            return Result.success("销售人员信息修改成功");
        } else {
            return Result.error("销售人员信息修改失败");
        }
    }

    /**
     * 生成发货单
     * @param contractId 合同ID
     * @return 生成结果
     */
    @PostMapping("/shipment/generate/{contractId}")
    public Result<String> generateShipment(@PathVariable Integer contractId) {
        boolean success = deliveryInfoService.generateDeliveryInfo(contractId);
        if (success) {
            return Result.success("发货单生成成功");
        } else {
            return Result.error("发货单生成失败：合同未支付或其他错误");
        }
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
     * 修改合同接口
     * @param contractUpdateDTO 合同更新信息
     * @return 返回执行结果
     */
    @PostMapping("/contract/update")
    public Result<String> updateContract(@RequestBody ContractUpdateDTO contractUpdateDTO) {
        String result = contractService.updateContract(contractUpdateDTO);
        if (result.startsWith("合同修改失败")) {
            return Result.error(result);
        }
        return Result.success(result);
    }
}
