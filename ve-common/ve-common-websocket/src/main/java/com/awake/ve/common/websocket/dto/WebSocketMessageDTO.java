package com.awake.ve.common.websocket.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 消息的DTO
 *
 * @author wangjiaxing
 * @date 2025/2/10 15:22
 */
@Data
public class WebSocketMessageDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 需要推送的session key列表
     */
    private List<Long> sessionKeys;

    /**
     * 需要推送的消息
     */
    private String message;
}
