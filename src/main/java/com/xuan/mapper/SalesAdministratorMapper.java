package com.xuan.mapper;

import com.xuan.pojo.entity.SalesAdministrator; // 假设您有一个 SalesAdministrator 实体类
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SalesAdministratorMapper {

    @Select("SELECT * FROM sales_administrator WHERE name = #{username} AND password = #{password}")
    SalesAdministrator findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
