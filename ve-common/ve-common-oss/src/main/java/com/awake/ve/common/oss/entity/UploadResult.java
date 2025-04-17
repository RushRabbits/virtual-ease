package com.awake.ve.common.oss.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadResult {

    /**
     * 文件访问路径
     */
    private String url;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 文件唯一标识
     */
    private String etag;
}
