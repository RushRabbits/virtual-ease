package com.awake.ve.system.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadRequestBo {

    /**
     * 文件hash
     */
    private String hash;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 分片序号
     */
    private Long fragmentNum;

    /**
     * 文件名
     */
    private String fileName;

    public UploadRequestBo(String hash, String type, Long num) {
        this.hash = hash;
        this.type = type;
        this.fragmentNum = num;
    }
}
