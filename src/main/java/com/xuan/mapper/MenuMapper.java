package com.xuan.mapper;

import com.xuan.pojo.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuMapper {

    @Select("SELECT * FROM menu WHERE role = #{role}")
    List<Menu> getMenusByRole(String role);
}
