package com.awake.ve.virtual.domain.bo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VeCloneVmByTemplateBo {

    /**
     * 模板id
     */
    @NotNull(message = "模板id不能为空")
    @Min(value = 100, message = "模板id不能小于{value}")
    @Max(value = 999999999, message = "模板id不能大于{value}")
    private Long templateId;

    /**
     * 新建虚拟机的id
     */
    @NotNull(message = "新建的虚拟机id不能为空")
    @Min(value = 100, message = "新建的虚拟机id不能小于{value}")
    @Max(value = 999999999, message = "新建的虚拟机id不能大于{value}")
    private Long newId;

    /**
     * 是否完整克隆
     */
    private Boolean full;
}
