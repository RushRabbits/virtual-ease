package com.awake.ve.system.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * 分片操作前置的校验接口Vo
 *
 * @author wangjiaxing
 * @date 2025/2/14 15:10
 */
@Data
public class UploadCheckVo {
    /**
     * 完整文件路径（秒传用）
     */
    private String filePath;

    /**
     * 已上传的分片序号列表
     */
    private List<Long> fragmentNums;

    /**
     * 是否已完整上传
     */
    private boolean isCompleted;

    /**
     * 静态工厂方法，用于创建完整上传的响应
     *
     * @author wangjiaxing
     * @date 2025/2/14 10:32
     */
    public static UploadCheckVo ofCompleted(String filePath) {
        UploadCheckVo response = new UploadCheckVo();
        response.setFilePath(filePath);
        response.setCompleted(true);
        return response;
    }

    /**
     * 静态工厂方法，用于创建部分上传的响应
     *
     * @author wangjiaxing
     * @date 2025/2/14 10:33
     */
    public static UploadCheckVo ofPartial(List<Long> chunkNums) {
        UploadCheckVo response = new UploadCheckVo();
        response.setFragmentNums(chunkNums);
        response.setCompleted(false);
        return response;
    }
}