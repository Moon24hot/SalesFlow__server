package com.xuan.service;

import com.xuan.pojo.dto.CustomerDTO;
import com.xuan.pojo.entity.Customer;
import com.xuan.pojo.vo.CustomerVO;

import java.util.List;

public interface CustomerService {
    Customer findByName(String name);

    /**
     * 获取所有客户信息
     *
     * @return 客户列表
     */
    List<CustomerVO> getAllCustomers();

    /**
     * 新增客户
     *
     * @param customerDTO 客户信息
     * @return 是否成功
     */
    boolean addCustomer(CustomerDTO customerDTO);

    /**
     * 修改客户信息
     *
     * @param customerDTO 客户信息
     * @return 是否成功
     */
    boolean updateCustomer(CustomerDTO customerDTO);
}
