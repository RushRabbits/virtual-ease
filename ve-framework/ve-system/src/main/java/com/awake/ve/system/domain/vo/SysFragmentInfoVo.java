package com.awake.ve.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.awake.ve.system.domain.SysFragmentInfo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 分片视图对象 sys_fragment_info
 *
 * @author wangjiaxing
 * @date 2025-02-14
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysFragmentInfo.class)
public class SysFragmentInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long fragmentId;

    /**
     * 分片的路径
     */
    @ExcelProperty(value = "分片的路径")
    private String path;

    /**
     * 分片起始的字节
     */
    @ExcelProperty(value = "分片起始的字节")
    private Long start;

    /**
     * 分片结束的字节
     */
    @ExcelProperty(value = "分片结束的字节")
    private Long end;

    /**
     * 分片的大小
     */
    @ExcelProperty(value = "分片的大小")
    private Long size;

    /**
     * 分片的序号
     */
    @ExcelProperty(value = "分片的序号")
    private Long num;

    /**
     * 父文件的hash值
     */
    @ExcelProperty(value = "父文件的hash值")
    private String hash;

    /**
     * 父文件总字节
     */
    @ExcelProperty(value = "父文件总字节")
    private Long total;

    /**
     * 文件的类型(分片与父文件相同)
     */
    @ExcelProperty(value = "文件的类型(分片与父文件相同)")
    private String type;

    /**
     * 文件的后缀
     */
    @ExcelProperty(value = "文件的后缀")
    private String suffix;

    /**
     * 总分片数
     */
    @ExcelProperty(value = "总分片数")
    private Long sum;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
