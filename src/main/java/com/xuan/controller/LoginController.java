package com.xuan.controller;

import com.xuan.pojo.dto.LoginRequest;
import com.xuan.pojo.vo.LoginResponse;
import com.xuan.result.Result;
import com.xuan.service.UserService;
import com.xuan.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        String role = loginRequest.getRole();

        // 校验用户身份
        String userId = userService.validateUser(username, password, role);
        if (userId != null) {
            // 生成 JWT，包含用户名和用户 ID
            String token = jwtUtil.generateToken(username, userId);

            // 创建响应对象
            LoginResponse response = new LoginResponse();
            response.setToken(token);
            response.setMenus(userService.getMenusByRole(role));
            response.setRoutes(userService.getRoutesByRole(role));

            return Result.success(response);
        } else {
            return Result.error("用户名或密码错误");
        }
    }
}
