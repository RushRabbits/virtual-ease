package com.awake.ve.system.domain.bo;

import com.awake.ve.common.core.validate.AddGroup;
import com.awake.ve.common.core.validate.EditGroup;
import com.awake.ve.common.mybatis.core.domain.BaseEntity;
import com.awake.ve.system.domain.SysFileInfo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 本地文件管理业务对象 sys_file_info
 *
 * @author wangjiaxing
 * @date 2025-02-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysFileInfo.class, reverseConvertGenerate = false)
public class SysFileInfoBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long fileId;

    /**
     * 文件名
     */
    @NotBlank(message = "文件名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 原始文件名
     */
    @NotBlank(message = "原始文件名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String originName;

    /**
     * 文件类型
     */
    @NotBlank(message = "文件类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String type;

    /**
     * 文件哈希
     */
    private String hash;

    /**
     * 文件后缀
     */
    @NotBlank(message = "文件后缀不能为空", groups = { AddGroup.class, EditGroup.class })
    private String suffix;

    /**
     * 文件存储路径
     */
    @NotBlank(message = "文件存储路径不能为空", groups = { AddGroup.class, EditGroup.class })
    private String path;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;


}
