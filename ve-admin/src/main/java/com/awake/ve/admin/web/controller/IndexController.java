package com.awake.ve.admin.web.controller;

import com.awake.ve.common.core.config.VirtualEaseConfig;
import com.awake.ve.common.core.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页控制器
 *
 * @author wangjiaxing
 * @date 2024/12/12 19:26
 */
@RestController
@RequiredArgsConstructor
public class IndexController {

    private final VirtualEaseConfig virtualEaseConfig;

    @GetMapping("/")
    public String index() {
        return StringUtils.format("欢迎使用{}后台管理框架，当前版本：{}，请通过前端地址访问。", virtualEaseConfig.getName(), virtualEaseConfig.getVersion());
    }
}
