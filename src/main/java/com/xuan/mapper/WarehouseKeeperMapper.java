package com.xuan.mapper;

import com.xuan.pojo.entity.WarehouseKeeper; // 假设您有一个 WarehouseKeeper 实体类
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WarehouseKeeperMapper {

    @Select("SELECT * FROM warehouse_keeper WHERE name = #{username} AND password = #{password}")
    WarehouseKeeper findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
