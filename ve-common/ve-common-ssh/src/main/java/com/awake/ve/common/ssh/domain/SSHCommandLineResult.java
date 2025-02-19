package com.awake.ve.common.ssh.domain;

import com.awake.ve.common.core.constant.Constants;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.ssh.enums.FileType;
import lombok.Data;

import static com.awake.ve.common.core.constant.Constants.BLANK;

/**
 * 命令行结果对象
 * 封装命令行结果
 *
 * @author wangjiaxing
 * @date 2025/2/19 14:46
 */
@Data
public class SSHCommandLineResult {

    /**
     * 文件类型
     */
    private FileType type;

    /**
     * 权限
     */
    private String permissions;

    /**
     * 链接数
     */
    private Integer linkCount;

    /**
     * 文件创建人
     */
    private String owner;

    /**
     * 文件创建组
     */
    private String group;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件修改时间
     */
    private String modificationDate;

    /**
     * 文件名
     */
    private String name;

    /**
     * 链接指向的文件名
     */
    private String linkTarget;

    /**
     * 解析命令行结果
     *
     * @param line 命令行字符串
     * @return {@link SSHCommandLineResult}
     * @author wangjiaxing
     * @date 2025/2/19 14:56
     */
    public static SSHCommandLineResult parse(String line) {
        SSHCommandLineResult commandLineResult = new SSHCommandLineResult();

        // 切割字符串为9个部分
        String[] parts = StringUtils.split(line, "\\s+");

        // 通常linux展示信息至少需要8个字段，所以如果小于8个字段则直接返回null
        if (parts.length < 8) {
            return null;
        }

        // 文件类型
        String typeAndPerms = parts[0];
        commandLineResult.type = FileType.fromChar(typeAndPerms.charAt(0));
        commandLineResult.permissions = StringUtils.substring(typeAndPerms, 1);
        // 连接数
        commandLineResult.linkCount = Integer.parseInt(parts[1]);
        // 文件创建人
        commandLineResult.owner = parts[2];
        // 文件创建组
        commandLineResult.group = parts[3];
        // 文件大小
        commandLineResult.size = Long.parseLong(parts[4]);
        // 修改时间
        commandLineResult.modificationDate = parts[5] + BLANK + parts[6] + BLANK + parts[7];

        // 文件名与符号链接
        if (parts.length == 8) {
            return commandLineResult;
        }

        StringBuilder nameBuilder = new StringBuilder(parts[8]);
        // 解析文件名及符号链接
        if (commandLineResult.type == FileType.SYMBOLIC_LINK && parts.length > 10 && parts[9].equals(Constants.SYMBOLIC_LINK_POINT)) {
            commandLineResult.linkTarget = parts[10];
            for (int i = 11; i < parts.length; i++) {
                commandLineResult.linkTarget += BLANK + parts[i];
            }
        } else {
            for (int i = 9; i < parts.length; i++) {
                nameBuilder.append(BLANK).append(parts[i]);
            }
        }
        commandLineResult.name = nameBuilder.toString();

        return commandLineResult;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FileInfo{");
        sb.append("type=").append(type);
        sb.append(", permissions='").append(permissions).append('\'');
        sb.append(", linkCount=").append(linkCount);
        sb.append(", owner='").append(owner).append('\'');
        sb.append(", group='").append(group).append('\'');
        sb.append(", size=").append(size);
        sb.append(", modificationDate='").append(modificationDate).append('\'');
        sb.append(", name='").append(name).append('\'');
        if (linkTarget != null) {
            sb.append(", linkTarget='").append(linkTarget).append('\'');
        }
        sb.append('}');
        return sb.toString();
    }
}
