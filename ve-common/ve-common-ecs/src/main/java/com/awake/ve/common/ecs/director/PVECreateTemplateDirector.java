package com.awake.ve.common.ecs.director;

import com.awake.ve.common.ecs.api.request.BaseApiRequest;
import com.awake.ve.common.ecs.api.template.PVECreateTemplateApiRequest;
import com.awake.ve.common.ecs.director.base.BaseApiDirector;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class PVECreateTemplateDirector extends BaseApiDirector {

    private String node;
    private String vmId;

    /**
     * 构建{@link BaseApiRequest}
     *
     * @return {@link BaseApiRequest}
     * @author wangjiaxing
     * @date 2025/2/22 15:48
     */
    public BaseApiRequest buildRequest() {
        return PVECreateTemplateApiRequest.builder()
                .node(this.node)
                .vmId(this.vmId)
                .build();
    }
}
