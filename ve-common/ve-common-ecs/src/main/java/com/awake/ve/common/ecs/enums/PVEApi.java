package com.awake.ve.common.ecs.enums;

import com.awake.ve.common.ecs.domain.apiResult.PVETicketApiResult;
import com.awake.ve.common.ecs.wrapper.PVEApiResultWrapper;
import com.awake.ve.common.ecs.wrapper.base.ApiWrapper;
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
    TICKET_CREATE(
            "http://{host}:{port}/api2/json/access/ticket?username={username}&password={password}",
            "pvesh post /access/ticket",
            HttpMethod.POST,
            newApiWrapper(),
            "创建ticket,ticket是调用api的token"
    ),


    ;

    private final String api;
    private final String cli;
    private final HttpMethod httpMethod;
    private final ApiWrapper apiWrapper;
    private final String description;


    PVEApi(String api, String cli, HttpMethod httpMethod, ApiWrapper apiWrapper, String description) {
        this.api = api;
        this.cli = cli;
        this.httpMethod = httpMethod;
        this.apiWrapper = apiWrapper;
        this.description = description;
    }

    private static ApiWrapper newApiWrapper() {
        PVEApiResultWrapper wrapper = PVEApiResultWrapper.newInstance();
        wrapper.setApiResult(PVETicketApiResult.newInstance());
        return wrapper;
    }
}
