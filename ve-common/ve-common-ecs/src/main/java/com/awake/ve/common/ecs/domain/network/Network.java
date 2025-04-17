package com.awake.ve.common.ecs.domain.network;

import com.awake.ve.common.ecs.enums.network.NetworkConfigMethod;
import com.awake.ve.common.ecs.enums.network.NetworkType;
import lombok.Data;

import java.util.List;

/**
 * 网络抽象类
 *
 * @author wangjiaxing
 * @date 2025/2/26 14:45
 */
@Data
public abstract class Network {

    /**
     * 接口名称，这是一个名为vmbr0的虚拟网桥
     */
    protected String iface;
    /**
     * 网络类型
     * {@link NetworkType}
     */
    protected String type;
    /**
     * 表示这个接口已经存在于系统中
     */
    protected Boolean exists;
    /**
     * IP地址分配方法
     * {@link NetworkConfigMethod}
     */
    protected String method;
    /**
     * IPv6地址分配方法
     * {@link NetworkConfigMethod}
     */
    private String method6;
    /**
     * 系统启动时自动启动此网络接口
     */
    protected Boolean autostart;

    /**
     * 表示此接口当前处于活动状态
     */
    protected Boolean active;

    /**
     * 网络优先级
     */
    protected Integer priority;
    /**
     * 支持的地址族
     */
    protected List<String> families;

    public Network() {
    }
}
