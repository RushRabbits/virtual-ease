package com.awake.ve.system.converter;

import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.system.domain.SysFileInfo;
import com.awake.ve.system.domain.vo.FileInfoVo;

public class FileInfoConverter {

    /**
     * 构建文件信息
     *
     * @param hash           文件哈希值
     * @param fileName       文件名
     * @param originFileName 原始文件名
     * @param path           文件路径
     * @param suffix         文件路径
     * @param fileType       文件类型
     * @return {@link SysFileInfo}
     * @author wangjiaxing
     * @date 2025/2/17 16:08
     */
    public static SysFileInfo buildFileInfo(String hash, String fileName, String originFileName, String path, String suffix, String fileType) {
        SysFileInfo fileInfo = new SysFileInfo();
        fileInfo.setHash(hash);
        fileInfo.setName(fileName);
        fileInfo.setOriginName(originFileName);
        fileInfo.setPath(path);
        fileInfo.setSuffix(suffix);
        fileInfo.setType(fileType);
        return fileInfo;
    }

    /**
     * 构建文件信息Vo
     *
     * @param fileInfo {@link SysFileInfo}
     * @return {@link FileInfoVo}
     * @author wangjiaxing
     * @date 2025/2/17 16:10
     */
    public static FileInfoVo buildFileInfoVo(SysFileInfo fileInfo, String host, String location) {
        FileInfoVo vo = new FileInfoVo();
        vo.setFileId(fileInfo.getFileId());
        vo.setFileType(fileInfo.getType());
        vo.setFileName(fileInfo.getName());
        vo.setFilePath(host + fileInfo.getPath());
        String local = StringUtils.replace(location, "\\", "/").replaceAll("/+", "/");
        vo.setFileLocation(local);
        return vo;
    }
}
