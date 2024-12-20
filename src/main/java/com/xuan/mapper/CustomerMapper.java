package com.xuan.mapper;

import com.xuan.pojo.entity.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CustomerMapper {
    @Select("SELECT * FROM customer WHERE name = #{name}")
    Customer findByName(String name);
    /**
     * 查询所有客户
     *
     * @return 客户列表
     */
    @Select("SELECT * FROM customer")
    List<Customer> findAllCustomers();

    /**
     * 新增客户
     *
     * @param customer 客户实体
     * @return 插入的记录数
     */
    @Insert("INSERT INTO customer (name, telegram) VALUES (#{name}, #{telegram})")
    int insertCustomer(Customer customer);

    /**
     * 查询客户根据 ID
     *
     * @param id 客户ID
     * @return 客户实体
     */
    @Select("SELECT * FROM customer WHERE id = #{id}")
    Customer findCustomerById(Integer id);

    /**
     * 修改客户信息
     *
     * @param customer 客户实体
     * @return 更新的记录数
     */
    @Update("UPDATE customer SET name = #{name}, telegram = #{telegram} WHERE id = #{id}")
    int updateCustomer(Customer customer);

    /**
     * 根据姓名查询客户ID
     *
     * @param name 客户姓名
     * @return 客户ID
     */
    @Select("SELECT id FROM customer WHERE name = #{name}")
    Integer findCustomerIdByName(String name);
}
