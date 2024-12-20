package com.xuan.service.impl;

import com.xuan.mapper.CustomerMapper;
import com.xuan.pojo.dto.CustomerDTO;
import com.xuan.pojo.entity.Customer;
import com.xuan.pojo.vo.CustomerVO;
import com.xuan.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Customer findByName(String name) {
        return customerMapper.findByName(name);
    }

    /**
     * 查询客户列表
     * @return
     */
    @Override
    public List<CustomerVO> getAllCustomers() {
        // 查询所有客户并转换为 VO
        return customerMapper.findAllCustomers().stream().map(customer -> {
            CustomerVO vo = new CustomerVO();
            vo.setId(customer.getId());
            vo.setName(customer.getName());
            vo.setTelegram(customer.getTelegram());
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean addCustomer(CustomerDTO customerDTO) {
        // 创建 Customer 实体并保存到数据库
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setTelegram(customerDTO.getTelegram());

        return customerMapper.insertCustomer(customer) > 0; // 返回插入结果
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) {
        // 查询客户是否存在
        Customer existingCustomer = customerMapper.findCustomerById(customerDTO.getId());
        if (existingCustomer != null) {
            existingCustomer.setName(customerDTO.getName());
            existingCustomer.setTelegram(customerDTO.getTelegram());
            return customerMapper.updateCustomer(existingCustomer) > 0; // 返回更新结果
        }
        return false; // 如果客户不存在，返回 false
    }
}
