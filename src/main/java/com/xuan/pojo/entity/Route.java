package com.xuan.pojo.entity;

import lombok.Data;

@Data
public class Route {
    private String path;
    private String component;
    private String role;
    private String routeName;
    private String parentRoute;
}
