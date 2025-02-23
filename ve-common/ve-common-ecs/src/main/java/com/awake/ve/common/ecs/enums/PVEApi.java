package com.awake.ve.common.ecs.enums;

import com.awake.ve.common.ecs.api.request.BaseApiRequest;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.handler.impl.*;
import com.awake.ve.common.ecs.handler.ApiHandler;
import lombok.Getter;
import org.springframework.http.HttpMethod;


/**
 * PVE的api枚举
 *
 * @author wangjiaxing
 * @date 2025/2/21 18:40
 */
@Getter
public enum PVEApi {
    /**
     * 创建ticket
     */
    TICKET_CREATE(
            "http://{host}:{port}/api2/json/access/ticket?username={username}&password={password}",
            "pvesh post /access/ticket",
            HttpMethod.POST,
            PVETicketApiHandler.newInstance(),
            "创建ticket,ticket是调用api的token"
    ),
    /**
     * 创建模板
     */
    CREATE_TEMPLATE(
            "http://{host}:{port}/api2/json/nodes/{node}/qemu/{vmid}/template",
            "pvesh create /nodes/{node}/qemu/{vmid}/template",
            HttpMethod.POST,
            PVECreateTemplateApiHandler.newInstance(),
            "根据虚拟机创建模板(虚拟机需关闭)"
    ),
    /**
     * 模板克隆虚拟机
     */
    TEMPLATE_CLONE_VM(
            "/api2/json/nodes/{node}/qemu/{vmid}/clone",
            "pvesh create /nodes/{node}/qemu/{vmid}/clone",
            HttpMethod.POST,
            PVETemplateCreateVmApiHandler.newInstance(),
            "注意克隆分为两种模式,链接克隆和完整克隆,链接克隆本身可以保证统一模板创建的不通虚拟机之间的数据隔离(qcow2格式的虚拟机在数据隔离层面做的更好),所以我们默认采用链接克隆,效率更高"
    ),

    /**
     * 启动虚拟机
     * Path:
     * Start virtual machine.
     * HTTP:   	POST /api2/json/nodes/{node}/qemu/{vmid}/status/start
     * <p>
     * CLI:	pvesh create /nodes/{node}/qemu/{vmid}/status/start
     */
    START_VM(
            "http://{host}:{port}/api2/json/nodes/{node}/qemu/{vmid}/status/start",
            "pvesh create /nodes/{node}/qemu/{vmid}/clone",
            HttpMethod.POST,
            PVEStartVmApiHandler.newInstance(),
            "启动虚拟机"
    ),

    /**
     * 关闭虚拟机(按下关机键)
     */
    SHUTDOWN_VM(
            "http://{host}:{port}/api2/json/nodes/{node}/qemu/{vmid}/status/shutdown",
            "pvesh create /nodes/{node}/qemu/{vmid}/status/shutdown",
            HttpMethod.POST,
            PVEShutdownVmApiHandler.newInstance(),
            "关闭虚拟机 此api相当于按下关机按钮"
    ),

    /**
     * 从速率上考虑,更推荐这个
     * 关闭虚拟机(拔掉电源)
     */
    STOP_VM(
            "http://{host}:{port}/api2/json/nodes/{node}/qemu/{vmid}/status/stop",
            "pvesh create /nodes/{node}/qemu/{vmid}/status/stop",
            HttpMethod.POST,
            PVEStopVmApiHandler.newInstance(),
            "关闭虚拟机 此api相当于拔掉电源"
    ),

    /**
     * 重启虚拟机
     * TODO 不推荐使用,因为有时虚拟机会关闭失败导致无法重启,原因暂时不知道
     * TODO 有重启的需求建议使用stop + start实现
     */
    REBOOT_VM(
            "http://{host}:{port}/api2/json/nodes/{node}/qemu/{vmid}/status/reboot",
            "pvesh create /nodes/{node}/qemu/{vmid}/status/reboot",
            HttpMethod.POST,
            PVERebootVmApiHandler.newInstance(),
            "重启虚拟机"

    ),
    /**
     * 销毁虚拟机(必须先关闭虚拟机)
     */
    DESTROY_VM(
            "http://{host}:{port}/api2/json/nodes/{node}/qemu/{vmid}?",
            "pvesh delete /nodes/{node}/qemu/{vmid}",
            HttpMethod.DELETE,
            PVEDestroyVmApiHandler.newInstance(),
            "销毁虚拟机"
    );

    private final String api;
    private final String cli;
    private final HttpMethod httpMethod;
    private final ApiHandler apiHandler;
    private final String description;


    PVEApi(String api, String cli, HttpMethod httpMethod, ApiHandler apiHandler, String description) {
        this.api = api;
        this.cli = cli;
        this.httpMethod = httpMethod;
        this.apiHandler = apiHandler;
        this.description = description;
    }

    public BaseApiResponse handle() {
        return this.getApiHandler().handle();
    }

    public BaseApiResponse handle(BaseApiRequest request) {
        return this.getApiHandler().handle(request);
    }
}
