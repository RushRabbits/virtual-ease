package com.awake.ve.common.ssh.utils;

import com.awake.ve.common.core.exception.ServiceException;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.ssh.config.properties.SSHProperties;
import com.awake.ve.common.ssh.domain.SSHCommandLineResult;
import com.awake.ve.common.ssh.domain.SSHCommandResult;
import com.awake.ve.common.ssh.domain.dto.SSHCommandDTO;
import com.awake.ve.common.ssh.enums.ChannelType;
import com.jcraft.jsch.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
    private final SSHProperties SSH_PROPERTIES;

    /**
     * jsch session连接池
     */
    private static final GenericObjectPool<Session> JSCH_SESSION_POOL = SpringUtils.getBean("jschSessionPool");

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
     * @param commandDTO {@link SSHCommandDTO}
     * @return {@link SSHCommandResult}
     * @author wangjiaxing
     * @date 2025/2/19 15:59
     */
    public static SSHCommandResult sendCommand(SSHCommandDTO commandDTO) {
        Channel channel = null;
        Session session = null;
        try {
            String channelType = commandDTO.getChannelType();
            Queue<String> commands = commandDTO.getCommands();
            if (StringUtils.isBlank(channelType)) {
                channelType = ChannelType.EXEC.getType();
            }
            session = openSession();
            channel = openChannel(session, channelType);
            return send(channel, commands);
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
     * 发送命令
     *
     * @param channel  {@link Channel}
     * @param commands 命令队列
     * @return {@link com.awake.ve.common.ssh.domain.SSHCommandResult}
     * @author wangjiaxing
     * @date 2025/2/19 16:08
     */
    public static SSHCommandResult send(Channel channel, Queue<String> commands) {
        if (channel instanceof ChannelShell) {
            return sendShell(channel, commands);
        } else if (channel instanceof ChannelExec) {
            return sendExec(channel, commands);
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
     * @param channel  {@link Channel}
     * @param commands 命令队列
     * @return {@link com.awake.ve.common.ssh.domain.SSHCommandResult}
     * @author wangjiaxing
     */
    private static SSHCommandResult sendExec(Channel channel, Queue<String> commands) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); ByteArrayOutputStream errorStream = new ByteArrayOutputStream()) {
            ChannelExec channelExec = (ChannelExec) channel;
            while (!commands.isEmpty()) {
                channelExec.setCommand(commands.poll());
            }

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
     * @param channel  {@link Channel}
     * @param commands 命令队列
     * @return {@link com.awake.ve.common.ssh.domain.SSHCommandResult}
     * @author wangjiaxing
     */
    private static SSHCommandResult sendShell(Channel channel, Queue<String> commands) {
        ChannelShell channelShell = (ChannelShell) channel;

        // 准备输入输出流
        try (PipedOutputStream commandStream = new PipedOutputStream();
             PipedInputStream pipedIn = new PipedInputStream(commandStream);
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
            while (!commands.isEmpty()) {
                commander.println(commands.poll());
            }

            Thread.sleep(1000);

            String output = outputStream.toString(StandardCharsets.UTF_8);
            String error = errorStream.toString(StandardCharsets.UTF_8);

            List<String> outputLines = Arrays.stream(StringUtils.split(output, "\n")).toList();
            outputLines = outputLines.stream()
                    .filter(line -> !line.trim().isEmpty())
                    .filter(line -> !line.matches(".*@.*:.*[#$].*"))
                    .toList();

            List<SSHCommandLineResult> SSHResultLineList = outputLines.stream().map(SSHCommandLineResult::parse).filter(Objects::nonNull).toList();
            return new SSHCommandResult(SSHResultLineList, error, 0);
        } catch (Exception e) {
            log.error("[SSHUtils][sendShell] 发送Shell命令失败", e);
            throw new ServiceException(e.getMessage());
        }
    }

}
