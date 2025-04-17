package com.awake.ve.virtual.domain;

import com.awake.ve.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 镜像管理对象 ve_image_info
 *
 * @author wangjiaxing
 * @date 2025-02-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ve_image_info")
public class VeImageInfo extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 镜像id
     */
    @TableId(value = "image_id")
    private Long imageId;

    /**
     * 镜像文件的id
     */
    private Long fileId;

    /**
     * 镜像名
     */
    private String imageName;

    /**
     * 镜像格式
     */
    private String imageFormat;

    /**
     * 操作系统类型
     */
    private String osType;

    /**
     * 镜像大小
     */
    private Long size;

    /**
     * 描述
     */
    private String description;

    /**
     * 镜像存储位置
     */
    private String location;

    /**
     * 备注
     */
    private String remark;


}
