package com.awake.ve.common.ecs.converter;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.api.vm.status.PVENodeVmListApiResponse;
import com.awake.ve.common.ecs.api.vm.status.PVEVmStatusApiResponse;
import com.awake.ve.common.ecs.domain.PveHaObject;
import com.awake.ve.common.ecs.domain.PveVmInfo;

import java.util.ArrayList;
import java.util.List;

import static com.awake.ve.common.ecs.constants.JsonPathConstants.*;

public class EcsConverter {

    /**
     * @param json {@link JSON}
     * @return {@link BaseApiResponse}
     * @author wangjiaxing
     * @date 2025/2/24 11:37
     */
    public static BaseApiResponse buildPVEVmStatusApiResponse(JSON json) {
        PveHaObject pveHaObject = new PveHaObject();

        Integer haManaged = json.getByPath(VM_STATUS_HA_MANAGED, Integer.class);
        pveHaObject.setManaged(haManaged);

        String status = json.getByPath(VM_STATUS_STATUS, String.class);
        Long vmId = json.getByPath(VM_STATUS_VMID, Long.class);
        String name = json.getByPath(VM_STATUS_NAME, String.class);
        Boolean agent = json.getByPath(VM_STATUS_QEMU_AGENT, Boolean.class);
        String lock = json.getByPath(VM_STATUS_LOCK, String.class);
        String clipboard = json.getByPath(VM_STATUS_CLIPBOARD, String.class);
        Integer cpus = json.getByPath(VM_STATUS_CPUS, Integer.class);
        Double maxDisk = json.getByPath(VM_STATUS_MAX_DISK, Double.class);
        Double maxMem = json.getByPath(VM_STATUS_MAXMEM, Double.class);
        Double diskRead = json.getByPath(VM_STATUS_DISK_READ, Double.class);
        Double diskWrite = json.getByPath(VM_STATUS_DISK_WRITE, Double.class);
        Double netIn = json.getByPath(VM_STATUS_NET_IN, Double.class);
        Double netOut = json.getByPath(VM_STATUS_NET_OUT, Double.class);
        Long pid = json.getByPath(VM_STATUS_QEMU_PID, Long.class);
        String qmpStatus = json.getByPath(VM_STATUS_QMP_STATUS, String.class);
        String runningMachine = json.getByPath(VM_STATUS_RUNNING_MACHINE, String.class);
        String runningQemu = json.getByPath(VM_STATUS_RUNNING_QEMU, String.class);
        Boolean spice = json.getByPath(VM_STATUS_SPICE, Boolean.class);
        String tags = json.getByPath(VM_STATUS_TAGS, String.class);
        Boolean template = json.getByPath(VM_STATUS_TEMPLATE, Boolean.class);
        Integer uptime = json.getByPath(VM_STATUS_UPTIME, Integer.class);

        return PVEVmStatusApiResponse.builder()
                .ha(pveHaObject)
                .agent(agent)
                .cpus(cpus)
                .diskRead(diskRead)
                .runningQemu(runningQemu)
                .name(name)
                .netIn(netIn)
                .netOut(netOut)
                .pid(pid)
                .status(status)
                .vmId(vmId)
                .lock(lock)
                .clipboard(clipboard)
                .maxDisk(maxDisk)
                .maxMem(maxMem)
                .diskWrite(diskWrite)
                .qmpStatus(qmpStatus)
                .runningMachine(runningMachine)
                .spice(spice)
                .tags(tags)
                .template(template)
                .uptime(uptime)
                .build();
    }

    /**
     * 构建虚拟机列表
     *
     * @param json 参数
     * @return {@link com.awake.ve.common.ecs.api.vm.status.PVENodeVmListApiResponse}
     * @author wangjiaxing
     * @date 2025/2/24 14:20
     */
    public static BaseApiResponse buildPVENodeVmListApiResponse(JSON json) {
        String data = json.getByPath(PVE_BASE_RESP, String.class);
        if (StringUtils.isBlank(data)) {
            return new PVENodeVmListApiResponse(new ArrayList<>());
        }

        JSONArray array = JSONUtil.parseArray(data);

        List<PveVmInfo> pveVmInfoList = array.stream().map(o -> {
            JSONObject jsonObject = JSONUtil.parseObj(o);
            String status = jsonObject.getByPath(VM_LIST_STATUS, String.class);
            Long vmId = jsonObject.getByPath(VM_LIST_VMID, Long.class);
            String name = jsonObject.getByPath(VM_LIST_NAME, String.class);
            String lock = jsonObject.getByPath(VM_LIST_LOCK, String.class);
            Integer cpus = jsonObject.getByPath(VM_LIST_CPUS, Integer.class);
            Double maxDisk = jsonObject.getByPath(VM_LIST_MAX_DISK, Double.class);
            Double maxMem = jsonObject.getByPath(VM_LIST_MAXMEM, Double.class);
            Double diskRead = jsonObject.getByPath(VM_LIST_DISK_READ, Double.class);
            Double diskWrite = jsonObject.getByPath(VM_LIST_DISK_WRITE, Double.class);
            Double netIn = jsonObject.getByPath(VM_LIST_NET_IN, Double.class);
            Double netOut = jsonObject.getByPath(VM_LIST_NET_OUT, Double.class);
            Long pid = jsonObject.getByPath(VM_LIST_QEMU_PID, Long.class);
            String qmpStatus = jsonObject.getByPath(VM_LIST_QMP_STATUS, String.class);
            String runningMachine = jsonObject.getByPath(VM_LIST_RUNNING_MACHINE, String.class);
            String runningQemu = jsonObject.getByPath(VM_LIST_RUNNING_QEMU, String.class);
            String tags = jsonObject.getByPath(VM_LIST_TAGS, String.class);
            Boolean template = jsonObject.getByPath(VM_LIST_TEMPLATE, Boolean.class);
            Integer uptime = jsonObject.getByPath(VM_LIST_UPTIME, Integer.class);

            return PveVmInfo.builder()
                    .status(status)
                    .vmId(vmId)
                    .name(name)
                    .lock(lock)
                    .cpus(cpus)
                    .maxDisk(maxDisk)
                    .maxMem(maxMem)
                    .diskRead(diskRead)
                    .diskWrite(diskWrite)
                    .netIn(netIn)
                    .netOut(netOut)
                    .pid(pid)
                    .qmpStatus(qmpStatus)
                    .runningMachine(runningMachine)
                    .runningQemu(runningQemu)
                    .tags(tags)
                    .template(template)
                    .uptime(uptime)
                    .build();

        }).toList();
        return new PVENodeVmListApiResponse(pveVmInfoList);
    }
}
