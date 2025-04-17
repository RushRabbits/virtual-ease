package com.awake.ve.common.ssh.enums;

import lombok.Getter;

@Getter
public enum ChannelType {
    SHELL("shell", "Shell 通道模拟一个完整的交互式终端会话，就像你直接通过终端登录到远程服务器一样。这种通道会保持会话状态，支持所有终端特性，比如命令历史、tab 补全等"),
    EXEC("exec", "Exec 通道用于执行单个命令，执行完成后通道就会关闭。这是最常用的通道类型，适合执行一次性的命令。"),
    SFTP("sftp", "SFTP 通道提供了安全的文件传输功能，支持文件上传、下载、目录操作等。"),
    SUB_SYSTEM("subsystem", "子系统通道用于启动特定的 SSH 子系统。这是一个较低级的接口，通常用于实现特定的协议或服务。SFTP 实际上就是一个子系统的例子。");

    private final String type;
    private final String description;

    ChannelType(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public static ChannelType fromType(String type) {
        for (ChannelType channelType : values()) {
            if (channelType.getType().equals(type)) {
                return channelType;
            }
        }
        return null;
    }
}
