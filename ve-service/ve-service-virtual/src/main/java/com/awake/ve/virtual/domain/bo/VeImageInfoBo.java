package com.awake.ve.virtual.domain.bo;

import com.awake.ve.common.core.validate.AddGroup;
import com.awake.ve.common.core.validate.EditGroup;
import com.awake.ve.common.mybatis.core.domain.BaseEntity;
import com.awake.ve.virtual.domain.VeImageInfo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 镜像管理业务对象 ve_image_info
 *
 * @author wangjiaxing
 * @date 2025-02-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = VeImageInfo.class, reverseConvertGenerate = false)
public class VeImageInfoBo extends BaseEntity {

    /**
     * 镜像id
     */
    @NotNull(message = "镜像id不能为空", groups = { EditGroup.class })
    private Long imageId;

    /**
     * 镜像文件的id
     */
    @NotNull(message = "镜像文件的id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long fileId;

    /**
     * 镜像名
     */
    @NotBlank(message = "镜像名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String imageName;

    /**
     * 镜像格式
     */
    @NotBlank(message = "镜像格式不能为空", groups = { AddGroup.class, EditGroup.class })
    private String imageFormat;

    /**
     * 操作系统类型
     */
    @NotBlank(message = "操作系统类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String osType;

    /**
     * 镜像大小
     */
    @NotNull(message = "镜像大小不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long size;

    /**
     * 描述
     */
    @NotBlank(message = "描述不能为空", groups = { AddGroup.class, EditGroup.class })
    private String description;

    /**
     * 镜像存储位置
     */
    @NotBlank(message = "镜像存储位置不能为空", groups = { AddGroup.class, EditGroup.class })
    private String location;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;


}
