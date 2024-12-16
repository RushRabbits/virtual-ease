package com.awake.ve.ecs.core.impl;

import com.awake.ve.ecs.core.EcsClient;

import java.util.Collection;
import java.util.List;

/**
 * PVE虚拟化服务实现类
 *
 * @author wangjiaxing
 * @date 2024/12/16 10:43
 */
public class PveClient implements EcsClient {
    @Override
    public Integer createVirtualMachine(Long templateId) {
        return 0;
    }

    @Override
    public Boolean deleteVirtualMachine(Long vmId) {
        return null;
    }

    @Override
    public Boolean startVirtualMachine(Long vmId) {
        return null;
    }

    @Override
    public Boolean stopVirtualMachine(Long vmId) {
        return null;
    }

    @Override
    public Boolean createNetwork() {
        return null;
    }

    @Override
    public Boolean deleteNetwork() {
        return null;
    }

    @Override
    public Boolean editNetwork() {
        return null;
    }

    @Override
    public Collection<Void> selectNetwork() {
        return List.of();
    }
}
