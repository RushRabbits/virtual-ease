package com.awake.ve.common.ecs.api.template;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 由虚拟机创建模板的api请求参数
 *
 * @author wangjiaxing
 * @date 2025/2/22 15:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PVECreateTemplateApiRequest extends PVEBaseApiRequest {

    /**
     * pve 节点
     */
    private String node;

    /**
     * 虚拟机id
     */
    private String vmId;

    public PVECreateTemplateApiRequest(String node, String vmId) {
        this.node = node;
        this.vmId = vmId;
    }
}

