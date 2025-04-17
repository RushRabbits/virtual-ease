package com.awake.ve.system.domain;

import com.awake.ve.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 分片对象 sys_fragment_info
 *
 * @author wangjiaxing
 * @date 2025-02-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_fragment_info")
public class SysFragmentInfo extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "fragment_id")
    private Long fragmentId;

    /**
     * 分片的路径
     */
    private String fragmentPath;

    /**
     * 分片起始的字节
     */
    private Long fragmentStartByte;

    /**
     * 分片结束的字节
     */
    private Long fragmentEndByte;

    /**
     * 分片的大小
     */
    private Long fragmentSize;

    /**
     * 分片的序号
     */
    private Long fragmentNum;

    /**
     * 父文件的hash值
     */
    private String fileHash;

    /**
     * 父文件总字节
     */
    private Long fileTotalByte;

    /**
     * 文件的类型(分片与父文件相同)
     */
    private String fileType;

    /**
     * 文件的后缀
     */
    private String fileSuffix;

    /**
     * 总分片数
     */
    private Long fileTotalFragments;

    /**
     * 备注
     */
    private String remark;


}
