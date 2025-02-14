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
    private String fragmentPath;

    /**
     * 分片起始的字节
     */
    @ExcelProperty(value = "分片起始的字节")
    private Long fragmentStartByte;

    /**
     * 分片结束的字节
     */
    @ExcelProperty(value = "分片结束的字节")
    private Long fragmentEndByte;

    /**
     * 分片的大小
     */
    @ExcelProperty(value = "分片的大小")
    private Long fragmentSize;

    /**
     * 分片的序号
     */
    @ExcelProperty(value = "分片的序号")
    private Long fragmentNum;

    /**
     * 父文件的hash值
     */
    @ExcelProperty(value = "父文件的hash值")
    private String fileHash;

    /**
     * 父文件总字节
     */
    @ExcelProperty(value = "父文件总字节")
    private Long fileTotalByte;

    /**
     * 文件的类型(分片与父文件相同)
     */
    @ExcelProperty(value = "文件的类型(分片与父文件相同)")
    private String fileType;

    /**
     * 文件的后缀
     */
    @ExcelProperty(value = "文件的后缀")
    private String fileSuffix;

    /**
     * 总分片数
     */
    @ExcelProperty(value = "总分片数")
    private Long fileTotalFragments;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
