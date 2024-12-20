package com.xuan.mapper;

import com.xuan.pojo.entity.Route;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RouteMapper {

    @Select("SELECT * FROM routes WHERE role = #{role}")
    List<Route> getRoutesByRole(String role);
}
