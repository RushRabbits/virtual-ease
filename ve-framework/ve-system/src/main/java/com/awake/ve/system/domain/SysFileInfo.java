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
 * @date 2025-02-13
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
    private String fileName;

    /**
     * 原始文件名
     */
    private String originName;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 文件存储路径
     */
    private String filePath;

    /**
     * 备注
     */
    private String remark;


}
