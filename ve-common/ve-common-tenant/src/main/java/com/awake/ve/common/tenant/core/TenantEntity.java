package com.awake.ve.common.tenant.core;

import com.awake.ve.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TenantEntity extends BaseEntity {
    /**
     * 租户id
     */
    private String tenantId;
}
