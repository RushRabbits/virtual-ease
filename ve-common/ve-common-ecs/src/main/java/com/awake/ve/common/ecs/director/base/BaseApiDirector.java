package com.awake.ve.common.ecs.director.base;

import com.awake.ve.common.ecs.api.request.BaseApiRequest;
import lombok.Data;

/**
 * 建造者模式-指挥器
 *
 * @author wangjiaxing
 * @date 2025/2/22 15:39
 */
@Data
public abstract class BaseApiDirector {

    private BaseApiRequest request;

}
