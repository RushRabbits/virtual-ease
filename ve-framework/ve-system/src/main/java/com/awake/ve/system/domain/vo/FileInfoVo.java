package com.awake.ve.system.domain.vo;

import lombok.Data;

/**
 * 文件信息回显
 *
 * @author wangjiaxing
 * @date 2025/2/17 14:05
 */
@Data
public class FileInfoVo {

    /**
     * 文件id
     */
    private Long fileId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件存储路径
     */
    private String filePath;

    /**
     * 文件位置
     */
    private String fileLocation;
}
