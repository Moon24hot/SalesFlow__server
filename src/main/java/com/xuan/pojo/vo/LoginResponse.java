package com.xuan.pojo.vo;

import lombok.Data;

@Data
public class LoginResponse {
    private String token; // 返回的JWT
    private Object menus; // 菜单信息
    private Object routes; // 路由信息
}
