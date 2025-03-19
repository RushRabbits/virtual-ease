package com.awake.ve.virtual.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import com.awake.ve.common.core.domain.R;
import com.awake.ve.common.ecs.domain.network.Network;
import com.awake.ve.virtual.service.IVeNetworkInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/ve/network")
public class VeNetworkInfoController {

    private final IVeNetworkInfoService veNetworkService;

    /**
     * 查询节点下的网络列表
     *
     * @author wangjiaxing
     * @date 2025/3/19 16:32
     */
    @SaIgnore
    @SaCheckPermission("ve:network:list")
    @GetMapping("/list")
    public R<List<Network>> networks() {
        return R.ok(veNetworkService.networks());
    }
}
