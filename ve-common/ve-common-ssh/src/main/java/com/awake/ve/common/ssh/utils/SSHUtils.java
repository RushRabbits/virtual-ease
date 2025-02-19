package com.awake.ve.common.ssh.utils;

import com.awake.ve.common.core.exception.ServiceException;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.ssh.config.properties.SSHProperties;
import com.awake.ve.common.ssh.domain.SSHCommandLineResult;
import com.awake.ve.common.ssh.domain.SSHCommandResult;
import com.awake.ve.common.ssh.enums.ChannelType;
import com.jcraft.jsch.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.io.*;
import java.util.*;

/**
 * SSH工具类
 *
 * @author wangjiaxing
 * @date 2025/2/19 10:48
 */
@Slf4j
@RequiredArgsConstructor
public class SSHUtils {

    /**
     * ssh属性
     */
    private static SSHProperties SSH_PROPERTIES;

    /**
     * jsch session连接池
     */
    private static GenericObjectPool<Session> JSCH_SESSION_POOL;

    /**
     * 打开session
     *
     * @return {@link Session}
     * @author wangjiaxing
     * @date 2025/2/19 15:26
     */
    public static Session openSession() {
        try {
            return JSCH_SESSION_POOL.borrowObject(30000);
        } catch (Exception e) {
            log.info("[SSHUtils][openSession] 获取jsch session失败", e);
            throw new ServiceException("获取jsch session失败");
        }
    }

    /**
     * 打开channel
     *
     * @param channelType channel类型
     * @return {@link Channel}
     * @author wangjiaxing
     * @date 2025/2/19 15:24
     */
    public static Channel openChannel(Session session, String channelType) {
        try {
            return session.openChannel(channelType);
        } catch (JSchException e) {
            log.info("[SSHUtils][openChannel] jsch session 开启channel失败", e);
            throw new ServiceException("jsch session 开启channel失败");
        }
    }

    /**
     * 发送命令
     *
     * @param command 命令
     * @return {@link SSHCommandResult}
     * @author wangjiaxing
     * @date 2025/2/19 15:59
     */
    public static SSHCommandResult sendCommand(String command, String channelType) {
        Channel channel = null;
        Session session = null;
        try {
            if (StringUtils.isBlank(channelType)) {
                channelType = ChannelType.EXEC.getType();
            }
            session = openSession();
            channel = openChannel(session, channelType);
            return send(channel, command);
        } catch (Exception e) {
            log.error("[SSHUtils][sendCommand] 发送命令失败", e);
            throw new ServiceException(e.getMessage());
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                JSCH_SESSION_POOL.returnObject(session);
            }
        }
    }

    /**
     * @param commands    命令列表
     * @param channelType 管道类型{@link com.awake.ve.common.ssh.enums.ChannelType}
     * @author wangjiaxing
     * @date 2025/2/19 16:01
     */
    public static List<SSHCommandResult> sendCommands(List<String> commands, String channelType) {
        if (StringUtils.isBlank(channelType)) {
            channelType = ChannelType.SHELL.getType();
        }
        List<SSHCommandResult> res = new ArrayList<>();
        for (String command : commands) {
            SSHCommandResult commandResult = sendCommand(command, channelType);
            res.add(commandResult);
        }
        return res;
    }

    /**
     * 发送命令
     *
     * @param channel {@link Channel}
     * @param command 命令
     * @return {@link com.awake.ve.common.ssh.domain.SSHCommandResult}
     * @author wangjiaxing
     * @date 2025/2/19 16:08
     */
    public static SSHCommandResult send(Channel channel, String command) {
        if (channel instanceof ChannelShell) {
            return sendShell(channel, command);
        } else if (channel instanceof ChannelExec) {
            return sendExec(channel, command);
        } else if (channel instanceof ChannelSftp) {
            // TODO 根据自身需求,提供具体实现
            return null;
        } else if (channel instanceof ChannelSubsystem) {
            // TODO 根据自身需求,提供具体实现
            return null;
        } else {
            return null;
        }
    }

    /**
     * exec类型的channel发送命令
     *
     * @param channel {@link Channel}
     * @param command 命令
     * @return {@link com.awake.ve.common.ssh.domain.SSHCommandResult}
     * @author wangjiaxing
     */
    private static SSHCommandResult sendExec(Channel channel, String command) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); ByteArrayOutputStream errorStream = new ByteArrayOutputStream()) {
            ChannelExec channelExec = (ChannelExec) channel;
            channelExec.setCommand(command);

            channelExec.setOutputStream(outputStream);
            channelExec.setExtOutputStream(errorStream);
            channelExec.connect();

            // 等待命令执行完成
            while (!channelExec.isClosed()) {
                Thread.sleep(10);
            }

            String output = outputStream.toString();
            String error = errorStream.toString();

            List<SSHCommandLineResult> outputLines = Arrays.stream(StringUtils.split(output, "\n")).map(SSHCommandLineResult::parse).toList();

            return new SSHCommandResult(outputLines, error, 0);
        } catch (Exception e) {
            log.error("[SSHUtils][sendExec] 发送命令失败", e);
            throw new ServiceException(e.getMessage());
        }

    }

    /**
     * shell类型的channel发送命令
     *
     * @param channel {@link Channel}
     * @param command 命令
     * @return {@link com.awake.ve.common.ssh.domain.SSHCommandResult}
     * @author wangjiaxing
     */
    private static SSHCommandResult sendShell(Channel channel, String command) {
        ChannelShell channelShell = (ChannelShell) channel;

        // 准备输入输出流
        try (PipedInputStream pipedIn = new PipedInputStream();
             PipedOutputStream commandStream = new PipedOutputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
             PrintStream commander = new PrintStream(commandStream)) {

            // 读取输入
            channelShell.setInputStream(pipedIn);
            // 获取返回值
            channelShell.setOutputStream(outputStream);
            channelShell.setExtOutputStream(errorStream);

            channelShell.connect(3000);

            // 执行命令
            commander.println(command);

            while (!channelShell.isClosed()) {
                Thread.sleep(10);
            }

            String output = outputStream.toString();
            String error = errorStream.toString();

            List<String> outputLines = Arrays.stream(StringUtils.split(output, "\n")).toList();
            outputLines = outputLines.stream()
                    .filter(line -> !line.trim().isEmpty())
                    .filter(line -> !line.matches(".*@.*:.*[#$].*"))
                    .toList();

            List<SSHCommandLineResult> SSHResultLineList = outputLines.stream().map(SSHCommandLineResult::parse).toList();
            return new SSHCommandResult(SSHResultLineList, error, 0);
        } catch (Exception e) {
            log.error("[SSHUtils][sendShell] 发送Shell命令失败", e);
            throw new ServiceException(e.getMessage());
        }
    }

}
