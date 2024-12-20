package com.xuan.pojo.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
    private String role; // 用户角色
}
