package com.awake.ve.common.ecs.domain.vm.param;

import lombok.Data;

@Data
public class Scsi {

    /**
     * 镜像存储的路径(import-from的参数)
     */
    private String imagePath;

    /**
     * 镜像编码
     */
    private String format;
}
