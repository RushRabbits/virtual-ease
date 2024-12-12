package com.awake.ve.admin.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页控制器
 *
 * @author wangjiaxing
 * @date 2024/12/12 19:26
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "欢迎使用virtual-ease后台管理框架，当前版本：V0.0.1，请通过前端地址访问。";
    }
}
