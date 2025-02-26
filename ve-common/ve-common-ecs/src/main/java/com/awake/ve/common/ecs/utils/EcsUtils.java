package com.awake.ve.common.ecs.utils;

import com.awake.ve.common.core.constant.CacheConstants;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.api.ticket.PVETicketApiResponse;
import com.awake.ve.common.ecs.enums.command.LinuxCommand;
import com.awake.ve.common.ecs.enums.api.PVEApi;
import com.awake.ve.common.ecs.enums.command.QemuCommand;
import com.awake.ve.common.ssh.domain.dto.SSHCommandDTO;
import com.awake.ve.common.ssh.enums.ChannelType;
import com.awake.ve.common.ssh.utils.SSHUtils;
import com.awake.ve.common.translation.utils.RedisUtils;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class EcsUtils {

    /**
     * 校验ticket并返回可用的ticket
     *
     * @author wangjiaxing
     * @date 2025/2/22 15:57
     */
    public static PVETicketApiResponse checkTicket() {
        PVETicketApiResponse response = RedisUtils.getCacheObject(CacheConstants.PVE_API_TICKET + CacheConstants.PVE_API_TICKET);
        if (response != null) {
            return response;
        }

        BaseApiResponse baseApiResponse = PVEApi.TICKET_CREATE.handle();
        if (baseApiResponse instanceof PVETicketApiResponse ticketApiResponse) {
            return ticketApiResponse;
        }
        return null;
    }

    /**
     * 移除虚拟机锁定的配置文件
     * 不移除此文件,有时会导致虚拟机无法关机
     * 如果api用户是root,可以设置skipLock为true,就无需此操作
     *
     * @param vmId 虚拟机id
     * @author wangjiaxing
     * @date 2025/2/23 10:58
     */
    public static void rmLockConf(Long vmId) {
        String command = LinuxCommand.RM_LOCK_CONF.getCommand();
        command = command.replace("{vmid}", vmId.toString());
        Queue<String> commands = new LinkedBlockingDeque<>();
        commands.offer(command);
        SSHCommandDTO dto = SSHCommandDTO.createSSHCommandDTO(commands, ChannelType.EXEC);
        SSHUtils.sendCommand(dto);
    }

    public static void forceShutdownVm(Long vmId) {
        String command = QemuCommand.VM_MUST_STOP.getCommand();
        command = command.replace("{vmId}", vmId.toString());
        Queue<String> commands = new LinkedBlockingDeque<>();
        commands.offer(command);
        SSHCommandDTO dto = SSHCommandDTO.createSSHCommandDTO(commands, ChannelType.EXEC);
        SSHUtils.sendCommand(dto);
    }
}
