package com.awake.ve.common.sse.utils;

import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.sse.core.SseEmitterManager;
import com.awake.ve.common.sse.dto.SseMessageDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SseMessageUtils {

    private final static SseEmitterManager MANAGER = SpringUtils.getBean(SseEmitterManager.class);

    /**
     * 发送消息
     *
     * @param userId  用户id
     * @param message 消息
     * @author wangjiaxing
     * @date 2025/2/10 14:42
     */
    public static void sendMessage(Long userId, String message) {
        MANAGER.sendMessage(userId, message);
    }

    /**
     * 发送消息(致全体)
     *
     * @param message 消息
     * @author wangjiaxing
     * @date 2025/2/10 14:43
     */
    public static void sendMessage(String message) {
        MANAGER.sendMessage(message);
    }

    /**
     * 发布SSE订阅消息
     *
     * @param message {@link SseMessageDTO}
     * @author wangjiaxing
     * @date 2025/2/10 14:45
     */
    public static void publishMessage(SseMessageDTO message) {
        MANAGER.publishMessage(message);
    }

    /**
     * 向所有的用户发布订阅的消息(群发)
     *
     * @param message 要发布的消息内容
     */
    public static void publishAll(String message) {
        MANAGER.publishAll(message);
    }

}

