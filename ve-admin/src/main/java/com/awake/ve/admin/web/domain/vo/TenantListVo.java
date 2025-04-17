package com.awake.ve.admin.web.domain.vo;

import com.awake.ve.system.domain.SysTenant;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 租户列表
 *
 * @author wangjiaxing
 * @date 2025/2/12 9:15
 */
@Data
@AutoMapper(target = SysTenant.class)
public class TenantListVo {

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 公司名
     */
    private String companyName;

    /**
     * 域名
     */
    private String domain;
}
