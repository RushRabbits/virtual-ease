package com.awake.ve.common.ecs.converter;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.api.vm.status.PVECreateOrRestoreVmApiRequest;
import com.awake.ve.common.ecs.api.vm.status.PVENodeVmListApiResponse;
import com.awake.ve.common.ecs.api.vm.status.PVEVmStatusApiResponse;
import com.awake.ve.common.ecs.domain.PveHaObject;
import com.awake.ve.common.ecs.domain.PveVmInfo;

import java.util.ArrayList;
import java.util.List;

import static com.awake.ve.common.ecs.constants.ApiParamConstants.*;
import static com.awake.ve.common.ecs.constants.JsonPathConstants.*;

/**
 * ecs 模块转换器
 *
 * @author wangjiaxing
 * @date 2025/2/25 12:14
 */
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

    /**
     * 构建json
     *
     * @param request {@link PVECreateOrRestoreVmApiRequest}
     * @return {@link JSONObject}
     * @author wangjiaxing
     * @date 2025/2/25 11:55
     */
    public static JSONObject buildJSONObject(PVECreateOrRestoreVmApiRequest request) {
        /**
         * 注意 以下api的参数,如果没填的话,一定传null
         * 例如boolean的值,false时传了false,就会导致最后的jsonBody中的结构包含很多多余的键值对,这就会导致PVE认为你有缺失的参数
         * 例如缺失force,archive
         */
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.set(VM_ID, request.getVmId());
        jsonObject.set(ACPI, request.getAcpi() != null && request.getAcpi() ? 1 : null);
        jsonObject.set(AFFINITY, request.getAffinity());
        jsonObject.set(AGENT, request.getAgent());
        jsonObject.set(AMD_SEV, request.getAmdSev());
        jsonObject.set(ARCH, request.getArch());
        jsonObject.set(ARCHIVE, request.getArchive());
        jsonObject.set(ARGS, request.getArgs());
        jsonObject.set(AUDIO0, request.getAudio0());
        jsonObject.set(AUTO_START, request.getAutoStart());
        jsonObject.set(BALLOON, request.getBalloon());
        jsonObject.set(BIOS, request.getBios());
        jsonObject.set(BOOT, request.getBoot());
        jsonObject.set(BOOK_DISK, request.getBootDisk());
        jsonObject.set(BW_LIMIT, request.getBwLimit());
        jsonObject.set(CD_ROM, request.getCdrom());
        jsonObject.set(CI_CUSTOM, request.getCiCustom());
        jsonObject.set(CI_PASSWORD, request.getCiPassword());
        jsonObject.set(CI_TYPE, request.getCiType());
        jsonObject.set(CI_UPGRADE, request.getCiUpgrade() != null && request.getCiUpgrade() ? 1 : null);
        jsonObject.set(CI_USER, request.getCiUser());
        jsonObject.set(CORES, request.getCores());
        jsonObject.set(CPU, request.getCpu());
        jsonObject.set(CPU_LIMIT, request.getCpuLimits());
        jsonObject.set(CPU_UNITS, request.getCpuUnits());
        jsonObject.set(DESCRIPTION, request.getDescription());
        jsonObject.set(EFI_DISK_0, request.getEfiDisk0());
        jsonObject.set(FORCE, request.getForce() != null && request.getForce() ? 1 : null);
        jsonObject.set(FREEZE, request.getFreeze() != null && request.getFreeze() ? 1 : null);
        jsonObject.set(HOOK_SCRIPT, request.getHookScript());
        jsonObject.set(HOSTPCI, request.getHostPci()); // TODO list
        jsonObject.set(HOT_PLUG, request.getHotPlug());
        jsonObject.set(HUGE_PAGES, request.getHugePages());
        jsonObject.set(IDE, request.getIde()); // TODO list
        jsonObject.set(IMPORT_WORKING_STORAGE, request.getImportWorkingStorage());
        jsonObject.set(IP_CONFIG, request.getIpConfig()); // TODO list
        jsonObject.set(IVSH_MEM, request.getIvshmem());
        jsonObject.set(KEEP_HUGE_PAGES, request.getKeepHugePages());
        jsonObject.set(KEY_BOARD, request.getKeyBoard());
        jsonObject.set(KVM, request.getKvm());
        jsonObject.set(LIVE_RESTORE, request.getLiveRestore());
        jsonObject.set(LOCAL_TIME, request.getLocalTime());
        jsonObject.set(LOCK, request.getLock());
        jsonObject.set(MACHINE, request.getMachine());
        jsonObject.set(MEMORY, request.getMemory());
        jsonObject.set(MIGRATE_DOWNTIME, request.getMigrateDowntime());
        jsonObject.set(MIGRATE_SPEED, request.getMigrateSpeed());
        jsonObject.set(NAME, request.getName());
        jsonObject.set(NAME_SERVER, request.getNameserver());
        jsonObject.set(NET, request.getNet()); // TODO list
        jsonObject.set(NUMA, request.getNuma()); // TODO list
        jsonObject.set(ON_BOOT, request.getOnBoot() != null && request.getOnBoot() ? 1 : null);
        jsonObject.set(OS_TYPE, request.getOsType());
        jsonObject.set(PARALLEL, request.getParallel()); // TODO list
        jsonObject.set(POOL, request.getPool());
        jsonObject.set(PROTECTION, request.getProtection());
        jsonObject.set(REBOOT, request.getReboot());
        jsonObject.set(RNG0, request.getRng0());
        jsonObject.set(SATA, request.getSata()); // TODO list
        jsonObject.set(SCSI, request.getScsi()); // TODO list
        jsonObject.set(SCSI_HW, request.getScsiHw());
        jsonObject.set(SEARCH_DOMAIN, request.getSearchDomain());
        jsonObject.set(SERIAL, request.getSerial()); // TODO list
        jsonObject.set(SHARES, request.getShares());
        jsonObject.set(SM_BIOS1, request.getSmBios1());
        jsonObject.set(SMP, request.getSmp());
        jsonObject.set(SOCKETS, request.getSockets());
        jsonObject.set(SPICE_ENHANCEMENTS, request.getSpiceEnhancements());
        jsonObject.set(SSH_KEYS, request.getSshKeys());
        jsonObject.set(START, request.getStart());
        jsonObject.set(START_UP, request.getStartup());
        jsonObject.set(START_DATE, request.getStartDate());
        jsonObject.set(STORAGE, request.getStorage());
        jsonObject.set(TABLET, request.getTablet());
        jsonObject.set(TAGS, request.getTags());
        jsonObject.set(TDF, request.getTdf());
        jsonObject.set(TEMPLATE, request.getTemplate());
        jsonObject.set(TPM_STATE0, request.getTpmState0());
        jsonObject.set(UNIQUE, request.getUnique());
        jsonObject.set(UNUSED, request.getUnused()); // TODO list
        jsonObject.set(USB, request.getUsb()); // TODO list
        jsonObject.set(V_CPUS, request.getVCpus());
        jsonObject.set(VGA, request.getVga());
        jsonObject.set(VIRTIO, request.getVirtio()); // TODO list
        jsonObject.set(VM_GEN_ID, request.getVmGenId());
        jsonObject.set(VM_STATE_STORAGE, request.getVmStateStorage());
        jsonObject.set(WATCH_DOG, request.getWatchDog());
        return jsonObject;
    }
}
