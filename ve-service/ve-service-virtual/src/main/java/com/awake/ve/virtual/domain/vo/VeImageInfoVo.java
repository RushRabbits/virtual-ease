package com.awake.ve.virtual.domain.vo;

import com.awake.ve.common.excel.annotation.ExcelDictFormat;
import com.awake.ve.common.excel.convert.ExcelDictConvert;
import com.awake.ve.virtual.domain.VeImageInfo;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;



/**
 * 镜像管理视图对象 ve_image_info
 *
 * @author wangjiaxing
 * @date 2025-02-13
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = VeImageInfo.class)
public class VeImageInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 镜像id
     */
    @ExcelProperty(value = "镜像id")
    private Long imageId;

    /**
     * 镜像名
     */
    @ExcelProperty(value = "镜像名")
    private String imageName;

    /**
     * 镜像格式
     */
    @ExcelProperty(value = "镜像格式", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ve_image_format")
    private String imageFormat;

    /**
     * 操作系统类型
     */
    @ExcelProperty(value = "操作系统类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ve_os_type")
    private String osType;

    /**
     * 镜像大小
     */
    @ExcelProperty(value = "镜像大小")
    private Long size;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String description;

    /**
     * 镜像存储位置
     */
    @ExcelProperty(value = "镜像存储位置")
    private String location;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
