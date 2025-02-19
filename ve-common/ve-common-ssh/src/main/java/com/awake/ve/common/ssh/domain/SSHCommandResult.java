package com.awake.ve.common.ssh.domain;

import lombok.Data;

import java.util.List;

/**
 * 对linux命令执行结果的封装
 *
 * @author wangjiaxing
 * @date 2025/2/19 14:52
 */
@Data
public class SSHCommandResult {

    /**
     * 命令执行结果集
     */
    private final List<SSHCommandLineResult> outputLines;

    /**
     * 错误消息
     */
    private final String errorOutput;

    /**
     * 退出状态
     */
    private final Integer exitStatus;

    public SSHCommandResult(List<SSHCommandLineResult> outputLines, String errorOutput, Integer exitStatus) {
        this.outputLines = outputLines;
        this.errorOutput = errorOutput;
        this.exitStatus = exitStatus;
    }
}
