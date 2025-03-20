package com.awake.ve.common.ecs.utils;

import cn.hutool.core.text.StrFormatter;
import com.awake.ve.common.core.constant.CacheConstants;
import com.awake.ve.common.core.constant.HttpStatus;
import com.awake.ve.common.core.exception.ServiceException;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.api.ticket.PVETicketApiResponse;
import com.awake.ve.common.ecs.domain.vm.param.*;
import com.awake.ve.common.ecs.enums.api.PVEApiParam;
import com.awake.ve.common.ecs.enums.command.LinuxCommand;
import com.awake.ve.common.ecs.enums.api.PVEApi;
import com.awake.ve.common.ecs.enums.command.QemuCommand;
import com.awake.ve.common.ssh.domain.dto.SSHCommandDTO;
import com.awake.ve.common.ssh.enums.ChannelType;
import com.awake.ve.common.ssh.utils.SSHUtils;
import com.awake.ve.common.translation.utils.RedisUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

import static com.awake.ve.common.ecs.constants.ApiParamConstants.*;

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
        throw new ServiceException("获取ticket失败", HttpStatus.WARN);
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

    /**
     * {@link IpConfig}转字符串
     *
     * @param ipConfig {@link IpConfig}
     * @return 创建虚拟机时的ipConfig字符串
     * @author wangjiaxing
     * @date 2025/3/20 9:56
     */
    public static List<String> ipConfig2String(List<IpConfig> ipConfig) {
        List<String> ipconfigList = new ArrayList<>();
        if (CollectionUtils.isEmpty(ipConfig)) {
            return ipconfigList;
        }

        String param = PVEApiParam.IP_CONFIG.getParam();

        Map<String, Object> ipParam = new HashMap<>();

        for (IpConfig ipConfigItem : ipConfig) {
            ipParam.put(IP, ipConfigItem.getIp());
            ipParam.put(IP6, ipConfigItem.getIp6());
            ipconfigList.add(StrFormatter.format(param, ipParam, true));
            ipParam.clear();
        }

        return ipconfigList;
    }

    /**
     * {@link Agent}转字符串
     *
     * @param agent {@link Agent}
     * @author wangjiaxing
     * @date 2025/3/20 10:05
     */
    public static String agent2String(Agent agent) {
        String param = PVEApiParam.AGENT.getParam();

        Map<String, Object> agentParam = new HashMap<>();

        agentParam.put(ENABLED, agent.getEnabled());
        agentParam.put(TYPE, agent.getType());

        return StrFormatter.format(param, agentParam, true);
    }

    /**
     * net 转字符串
     *
     * @param net {@link Net}
     * @author wangjiaxing
     * @date 2025/3/20 10:07
     */
    public static List<String> net2String(List<Net> net) {
        List<String> netList = new ArrayList<>();
        if (CollectionUtils.isEmpty(net)) {
            return netList;
        }
        String param = PVEApiParam.NET.getParam();
        Map<String, Object> netParam = new HashMap<>();

        for (Net n : net) {
            netParam.put(MODEL, n.getModel());
            netParam.put(BRIDGE, n.getBridge());
            netParam.put(FIREWALL, n.getFirewall());
            // netParam.put(VLAN_TAG, n.getVlanTag());
            netList.add(StrFormatter.format(param, netParam, true));
            netParam.clear();
        }

        return netList;
    }

    /**
     * scsi 转字符串
     *
     * @param scsi {@link Scsi}
     * @author wangjiaxing
     * @date 2025/3/20 10:11
     */
    public static List<String> scsi2String(List<Scsi> scsi) {
        List<String> scsiList = new ArrayList<>();
        if (CollectionUtils.isEmpty(scsi)) {
            return scsiList;
        }

        String param = PVEApiParam.SCSI.getParam();

        Map<String, Object> scsiParam = new HashMap<>();

        for (Scsi s : scsi) {
            scsiParam.put(IMAGE_PATH, s.getImagePath());
            scsiParam.put(FORMAT, s.getFormat());
            scsiList.add(StrFormatter.format(param, scsiParam, true));
            scsiParam.clear();
        }

        return scsiList;
    }

    /**
     * ide 转字符串
     *
     * @param ide {@link Ide}
     * @author wangjiaxing
     * @date 2025/3/20 10:14
     */
    public static List<String> ide2String(List<Ide> ide) {
        List<String> ideList = new ArrayList<>();
        if (CollectionUtils.isEmpty(ide)) {
            return ideList;
        }

        String param = PVEApiParam.IDE.getParam();

        Map<String, Object> ideParamMap = new HashMap<>();

        for (Ide i : ide) {
            ideParamMap.put(LOCAL, i.getLocal());
            ideList.add(StrFormatter.format(param, ideParamMap, true));
            ideParamMap.clear();
        }

        return ideList;
    }
}
