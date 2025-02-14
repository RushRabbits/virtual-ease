package com.awake.ve.system.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "file.fragment.upload")
public class FragmentUploadProperties {

    /**
     * 文件分片上传的服务器地址
     */
    private String host;

    /**
     * 文件分片上传的路径
     */
    private String path;

    /**
     * 文件分片大小限制
     */
    private Long fragmentMaxSize;
}
