package com.awake.ve.ecs.core;

import java.util.Collection;

/**
 * ECS服务的核心Client
 *
 * @author wangjiaxing
 * @date 2024/12/16 10:29
 */
public interface EcsClient {

    // TODO 接口返回值设置成布尔类型并不是好的方法,先暂定这么写,到具体写实现时再去更改

    /**
     * 根据模板创建虚拟机
     *
     * @param templateId 模板id
     * @return 虚拟机id
     * @author wangjiaxing
     * @date 2024/12/16 10:34
     */
    Integer createVirtualMachine(Long templateId);

    /**
     * 根据虚拟机id删除虚拟机
     *
     * @param vmId 虚拟机id
     * @return 删除结果
     * @author wangjiaxing
     * @date 2024/12/16 10:34
     */
    Boolean deleteVirtualMachine(Long vmId);

    /**
     * 启动虚拟机
     *
     * @param vmId 虚拟机id
     * @return 是否启动成功
     * @author wangjiaxing
     * @date 2024/12/16 10:36
     */
    Boolean startVirtualMachine(Long vmId);

    /**
     * 停止虚拟机
     *
     * @param vmId 虚拟机id
     * @return 是否停止成功
     * @author wangjiaxing
     * @date 2024/12/16 10:36
     */
    Boolean stopVirtualMachine(Long vmId);

    /**
     * 创建网络
     *
     * @return
     */
    Boolean createNetwork();

    /**
     * 删除网络
     *
     * @param
     * @return
     * @author wangjiaxing
     * @date 2024/12/16 10:40
     */
    Boolean deleteNetwork();

    /**
     * 编辑网络
     *
     * @param
     * @return
     * @author wangjiaxing
     * @date 2024/12/16 10:41
     */
    Boolean editNetwork();

    /**
     * 查询网络列表
     *
     * @param
     * @return
     * @author wangjiaxing
     * @date 2024/12/16 10:41
     */
    Collection<Void> selectNetwork();
}
