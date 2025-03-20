package com.awake.ve.virtual.domain.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VeCreateOrEditNetworkBo {
    /**
     * 节点
     */
    private String node;

    /**
     * 网络名
     */
    private String iface;

    /**
     * 网络类型
     */
    @NotBlank(message = "网络类型不能为空")
    private String type;
}
