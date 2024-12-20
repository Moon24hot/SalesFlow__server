package com.xuan.service;

import com.xuan.mapper.MenuMapper;
import com.xuan.mapper.RouteMapper;
import com.xuan.mapper.SaleSpersonMapper; // 引入销售人员 mapper
import com.xuan.mapper.WarehouseKeeperMapper; // 引入仓库管理员 mapper
import com.xuan.mapper.SalesAdministratorMapper; // 引入销售管理员 mapper
import com.xuan.pojo.entity.Menu;
import com.xuan.pojo.entity.Route;
import com.xuan.pojo.entity.SaleSperson;
import com.xuan.pojo.entity.WarehouseKeeper;
import com.xuan.pojo.entity.SalesAdministrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RouteMapper routeMapper;

    @Autowired
    private SaleSpersonMapper saleSpersonMapper; // 注入销售人员 mapper

    @Autowired
    private WarehouseKeeperMapper warehouseKeeperMapper; // 注入仓库管理员 mapper

    @Autowired
    private SalesAdministratorMapper salesAdministratorMapper; // 注入销售管理员 mapper

    // 校验用户，返回用户ID
    public String validateUser(String username, String password, String role) {
        if ("sale_sperson".equals(role)) {
            SaleSperson sperson = saleSpersonMapper.findByUsernameAndPassword(username, password);
            return sperson != null ? String.valueOf(sperson.getId()) : null; // 返回用户ID或null
        } else if ("warehouse_keeper".equals(role)) {
            WarehouseKeeper keeper = warehouseKeeperMapper.findByUsernameAndPassword(username, password);
            return keeper != null ? keeper.getId() : null; // 返回用户ID或null
        } else if ("sales_administrator".equals(role)) {
            SalesAdministrator admin = salesAdministratorMapper.findByUsernameAndPassword(username, password);
            return admin != null ? admin.getId() : null; // 返回用户ID或null
        }
        return null; // 未知角色返回null
    }

    // 根据角色获取菜单
    public List<Menu> getMenusByRole(String role) {
        return menuMapper.getMenusByRole(role);
    }

    // 根据角色获取路由
    public List<Route> getRoutesByRole(String role) {
        return routeMapper.getRoutesByRole(role);
    }
}
