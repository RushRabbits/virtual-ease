package com.awake.ve.system.domain;

import com.awake.ve.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 本地文件管理对象 sys_file_info
 *
 * @author wangjiaxing
 * @date 2025-02-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_file_info")
public class SysFileInfo extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "file_id")
    private Long fileId;

    /**
     * 文件名
     */
    private String name;

    /**
     * 原始文件名
     */
    private String originName;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件哈希
     */
    private String hash;

    /**
     * 文件后缀
     */
    private String suffix;

    /**
     * 文件存储路径
     */
    private String path;

    /**
     * 备注
     */
    private String remark;


}
