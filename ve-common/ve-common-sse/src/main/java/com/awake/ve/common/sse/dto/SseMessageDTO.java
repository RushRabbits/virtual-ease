package com.awake.ve.common.sse.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * SSE消息的DTO
 *
 * @author wangjiaxing
 * @date 2025/2/10 13:46
 */
@Data
public class SseMessageDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 需要推送的session key 列表
     */
    private List<Long> userIds;

    /**
     * 需要推送的消息
     */
    private String message;
}
