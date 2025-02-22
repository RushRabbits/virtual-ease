package com.awake.ve.common.ecs.enums;

import com.awake.ve.common.ecs.api.request.BaseApiRequest;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.director.base.BaseApiDirector;
import com.awake.ve.common.ecs.handler.impl.PVECreateTemplateApiHandler;
import com.awake.ve.common.ecs.handler.impl.PVETicketApiHandler;
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


    ;

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

    public BaseApiResponse handle(BaseApiDirector director) {
        return this.getApiHandler().handle(director);
    }
}
