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
}
