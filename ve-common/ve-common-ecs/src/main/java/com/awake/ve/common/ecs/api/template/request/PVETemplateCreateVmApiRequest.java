package com.awake.ve.common.ecs.api.template.request;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import com.awake.ve.common.ecs.enums.vm.PVEVmFormat;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * pve api 根据模板克隆虚拟机的请求
 *
 * @author wangjiaxing
 * @date 2025/2/22 22:15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PVETemplateCreateVmApiRequest extends PVEBaseApiRequest {
    /**
     * 新虚拟机id(必填)
     */
    private Long newId;
    /**
     * pve节点(必填)
     */
    private String node;
    /**
     * 原始虚拟机id(必填)
     */
    private Long vmId;
    /**
     * 非必填
     * I/O带宽限制，单位是KiB/s
     * 可以限制克隆过程的I/O带宽使用
     */
    private Integer bwlimit;
    /**
     * 描述(非必填)
     */
    private String description;
    /**
     * 虚拟机格式(非必填)
     * 只有完整clone模式才需要
     */
    private PVEVmFormat format;
    /**
     * 是否完整克隆(非必填)
     * 默认为false
     */
    private Boolean full = false;
    /**
     * 虚拟机名称(非必填)
     */
    private String name;
    /**
     * 存储池(非必填)
     */
    private String pool;
    /**
     * 快照名称(非必填)
     * 指定要基于哪个快照来创建克隆
     * 是源虚拟机的某个快照的名称
     * 特别用于链接克隆（linked clone）场景
     * 例如：源VM可能有多个快照点，比如"clean_state"、"with_tools"等，通过snapname指定使用哪个快照作为克隆的基础
     */
    private String snapname;
    /**
     * 完整克隆的存储池(非必填)
     */
    private String storage;
    /**
     * 目标节点
     * 仅当源VM在共享存储上时可用
     */
    private String target;

}
