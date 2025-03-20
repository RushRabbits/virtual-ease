package com.awake.ve.virtual.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.awake.ve.common.core.domain.R;
import com.awake.ve.common.ecs.domain.network.Network;
import com.awake.ve.virtual.domain.bo.VeCreateOrEditNetworkBo;
import com.awake.ve.virtual.domain.bo.VeDeleteNetworkBo;
import com.awake.ve.virtual.domain.bo.VeGetNetworkConfigBo;
import com.awake.ve.virtual.service.IVeNetworkInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @SaCheckPermission("ve:network:list")
    @GetMapping("/list")
    public R<List<Network>> networks() {
        return R.ok(veNetworkService.networks());
    }

    /**
     * 创建网络
     *
     * @param bo {@link VeCreateOrEditNetworkBo}
     * @author wangjiaxing
     * @date 2025/3/20 14:43
     */
    @SaCheckPermission("ve:network:create")
    @PostMapping("/create")
    public R<Boolean> create(@Validated @RequestBody VeCreateOrEditNetworkBo bo) {
        return R.ok(veNetworkService.create(bo));
    }

    /**
     * 获取网络配置
     *
     * @param bo {@link VeCreateOrEditNetworkBo}
     * @author wangjiaxing
     * @date 2025/3/20 15:00
     */
    @SaCheckPermission("ve:network:getConfig")
    @GetMapping("/getConfig")
    public R<Network> getConfig(VeGetNetworkConfigBo bo) {
        return R.ok(veNetworkService.getConfig(bo));
    }

    /**
     * 重新加载节点下的网络配置
     *
     * @author wangjiaxing
     * @date 2025/3/20 15:20
     */
    @SaCheckPermission("ve:network:reloadConfig")
    @GetMapping("/reloadConfig")
    public R<Boolean> reloadConfig() {
        return R.ok(veNetworkService.reloadConfig());
    }

    /**
     * 恢复节点下的网络配置至上一个版本
     *
     * @author wangjiaxing
     * @date 2025/3/20 15:27
     */
    @SaCheckPermission("ve:network:revertConfig")
    @DeleteMapping("/revertConfig")
    public R<Boolean> revertConfig() {
        return R.ok(veNetworkService.revertConfig());
    }

    /**
     * 删除网络
     *
     * @param bo {@link VeDeleteNetworkBo}
     * @author wangjiaxing
     * @date 2025/3/20 15:34
     */
    @SaCheckPermission("ve:network:delete")
    @DeleteMapping("/delete")
    public R<Boolean> delete(VeDeleteNetworkBo bo) {
        return R.ok(veNetworkService.delete(bo));
    }
}
