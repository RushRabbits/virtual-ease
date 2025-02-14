package com.awake.ve.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.awake.ve.system.domain.SysFileInfo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;



/**
 * 本地文件管理视图对象 sys_file_info
 *
 * @author wangjiaxing
 * @date 2025-02-14
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysFileInfo.class)
public class SysFileInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long fileId;

    /**
     * 文件名
     */
    @ExcelProperty(value = "文件名")
    private String name;

    /**
     * 原始文件名
     */
    @ExcelProperty(value = "原始文件名")
    private String originName;

    /**
     * 文件类型
     */
    @ExcelProperty(value = "文件类型")
    private String type;

    /**
     * 文件md5
     */
    @ExcelProperty(value = "文件md5")
    private String md5;

    /**
     * 文件后缀
     */
    @ExcelProperty(value = "文件后缀")
    private String suffix;

    /**
     * 文件存储路径
     */
    @ExcelProperty(value = "文件存储路径")
    private String path;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
