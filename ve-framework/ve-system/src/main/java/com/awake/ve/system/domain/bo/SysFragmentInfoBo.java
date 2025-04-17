package com.awake.ve.system.domain.bo;

import com.awake.ve.common.core.validate.AddGroup;
import com.awake.ve.common.core.validate.EditGroup;
import com.awake.ve.common.mybatis.core.domain.BaseEntity;
import com.awake.ve.system.domain.SysFragmentInfo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 分片业务对象 sys_fragment_info
 *
 * @author wangjiaxing
 * @date 2025-02-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysFragmentInfo.class, reverseConvertGenerate = false)
public class SysFragmentInfoBo extends BaseEntity {

    /**
     * 分片文件
     */
    @NotNull(message = "分片文件不能为空", groups = {AddGroup.class, EditGroup.class})
    private MultipartFile chunkData;

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = {EditGroup.class})
    private Long fragmentId;

    /**
     * 分片的路径
     */
    // @NotBlank(message = "分片的路径不能为空", groups = {AddGroup.class, EditGroup.class})
    private String path;

    /**
     * 分片起始的字节
     */
    @NotNull(message = "分片起始的字节不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long start;

    /**
     * 分片结束的字节
     */
    @NotNull(message = "分片结束的字节不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long end;

    /**
     * 分片的大小
     */
    @NotNull(message = "分片的大小不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long size;

    /**
     * 分片的序号
     */
    @NotNull(message = "分片的序号不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long num;

    /**
     * 父文件的hash值
     */
    @NotBlank(message = "父文件的hash值不能为空", groups = {AddGroup.class, EditGroup.class})
    private String hash;

    /**
     * 父文件总字节
     */
    @NotNull(message = "父文件总字节不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long total;

    /**
     * 文件的类型(分片与父文件相同)
     */
    @NotBlank(message = "文件的类型(分片与父文件相同)不能为空", groups = {AddGroup.class, EditGroup.class})
    private String type;

    /**
     * 文件的后缀
     */
    @NotBlank(message = "文件的后缀不能为空", groups = {AddGroup.class, EditGroup.class})
    private String suffix;

    /**
     * 总分片数
     */
    @NotNull(message = "总分片数不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long sum;

    /**
     * 备注
     */
    // @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
    private String remark;


}
