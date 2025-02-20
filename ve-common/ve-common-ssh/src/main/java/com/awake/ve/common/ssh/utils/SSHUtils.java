package com.awake.ve.common.ssh.utils;

import com.awake.ve.common.core.exception.ServiceException;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.core.utils.Threads;
import com.awake.ve.common.ssh.config.properties.SSHProperties;
import com.awake.ve.common.ssh.domain.SSHCommandLineResult;
import com.awake.ve.common.ssh.domain.SSHCommandResult;
import com.awake.ve.common.ssh.domain.dto.SSHCommandDTO;
import com.awake.ve.common.ssh.enums.ChannelType;
import com.awake.ve.common.ssh.enums.PtyType;
import com.jcraft.jsch.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Random;

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
            String channelType = commandDTO.getChannelType().getType();
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

            List<SSHCommandLineResult> outputLines = Arrays.stream(StringUtils.split(output, "\n")).map(SSHCommandLineResult::parse).filter(Objects::nonNull).toList();

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
        /**
         * PTY 是 Pseudo Terminal（伪终端）的缩写
         * 当设置为 true 时，会为 shell 会话分配一个伪终端
         * 伪终端模拟了一个真实的终端设备，提供：
         *      命令行提示符的正确显示
         *      命令历史记录
         *      命令行编辑功能
         *      终端颜色和格式化输出
         * 如果不设置 PTY，某些命令可能无法正常工作或显示不正确
         */
        channelShell.setPty(true);
        /**
         * 设置伪终端的类型为 VT102
         * VT102 是一种标准的终端类型，提供：
         *      光标控制
         *      文本格式化
         *      屏幕清除
         *      颜色支持
         * 其他常见的终端类型包括：
         *      "xterm"
         *      "ansi"
         *      "dumb"
         */
        channelShell.setPtyType(PtyType.VT102.getType());

        // 准备输入输出流
        try (PipedOutputStream commandStream = new PipedOutputStream();
             PipedInputStream pipedIn = new PipedInputStream(commandStream);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
             PrintStream commander = new PrintStream(commandStream, true, StandardCharsets.UTF_8)) {

            // 读取输入
            channelShell.setInputStream(pipedIn);
            // 获取返回值
            channelShell.setOutputStream(outputStream);
            channelShell.setExtOutputStream(errorStream);

            channelShell.connect(3000);

            List<String> allOutputLines = new ArrayList<>();
            // 执行命令
            String marker = "CMD_FINISHED_" + System.currentTimeMillis() + "_" + new Random().nextInt(10000);
            while (!commands.isEmpty()) {
                String command = commands.poll() + " && echo " + marker;
                commander.println(command);
                commander.flush();

                // 命令是否完成
                boolean commandCompleted = false;
                long startTime = System.currentTimeMillis();
                // command未完成且未超过30秒
                while (!commandCompleted && System.currentTimeMillis() - startTime < 30000) {
                    Threads.sleep(100);
                    // 保存当前输出流中的原始字节数据
                    String currentOutput = outputStream.toString(StandardCharsets.UTF_8);

                    // 如果不包含marker，还原数据
                    if (!currentOutput.contains(marker)) {
                        continue;
                    }

                    commandCompleted = true;
                    // 只记录最后一条命令的结果
                    // TODO 这里可以在参数管理加参数判断是否只取最后一行command的结果
                    if (!commands.isEmpty()) {
                        // 清空当前输出流,为下一个任务做准备
                        outputStream.reset();
                        continue;
                    }
                    List<String> outputLines = Arrays.stream(StringUtils.split(currentOutput, "\n"))
                            .filter(line -> !line.trim().isEmpty())
                            .filter(line -> !line.matches(".*@.*:.*[#$].*"))
                            .filter(line -> !line.contains(marker))
                            .toList();

                    allOutputLines.addAll(outputLines);
                }

                // 超过了30秒,command未完成,则
                if (!commandCompleted) {
                    log.error("[SSHUtils][sendShell] command:{} 执行超时", command);
                    throw new ServiceException("command执行超时: " + command);
                }
            }

            // 错误信息
            String error = errorStream.toString(StandardCharsets.UTF_8);
            // 解析所有输出行
            List<SSHCommandLineResult> SSHResultLineList = allOutputLines.stream().map(SSHCommandLineResult::parse).filter(Objects::nonNull).toList();
            return new SSHCommandResult(SSHResultLineList, error, 0);
        } catch (Exception e) {
            log.error("[SSHUtils][sendShell] 发送Shell命令失败", e);
            throw new ServiceException(e.getMessage());
        }
    }

}
