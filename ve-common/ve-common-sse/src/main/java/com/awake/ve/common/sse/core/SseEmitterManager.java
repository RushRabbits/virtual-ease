package com.awake.ve.common.sse.core;

import com.awake.ve.common.translation.utils.RedisUtils;
import com.awake.ve.common.sse.dto.SseMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * 管理 Server-Sent-Events (SSE) 连接
 *
 * @author wangjiaxing
 * @date 2025/2/10 13:50
 */
@Slf4j
@Component
public class SseEmitterManager {

    /**
     * 订阅的频道
     */
    private final static String SSE_TOPIC = "global:sse";

    private final static Map<Long, Map<String, SseEmitter>> USER_TOKEN_EMITTERS = new ConcurrentHashMap<>();

    /**
     * 建立与指定用户的SSE连接
     *
     * @param userId 用户id 用以区分不同用户的连接
     * @param token  用户的唯一令牌 用以识别具体的连接
     * @return {@link SseEmitter} 客户端可以通过该实例接收SSE事件
     * @author wangjiaxing
     * @date 2025/2/10 13:53
     */
    public SseEmitter connect(Long userId, String token) {
        // 从USER_TOKEN_EMITTERS中获取或创建当前用户的SseEmitter映射表
        // 每个用户可以有多个SSE连接,通过token进行区分
        Map<String, SseEmitter> emitters = USER_TOKEN_EMITTERS.computeIfAbsent(userId, k -> new ConcurrentHashMap<>());

        // 创建一个新的SseEmitter实例 , 超时时间设置为0 , 表示为无限制
        SseEmitter emitter = new SseEmitter(0L);

        emitters.put(token, emitter);

        // 当emitter完成,超时,或者发生错误时,从映射表移除对应的token
        emitter.onCompletion(() -> emitters.remove(token));
        emitter.onTimeout(() -> emitters.remove(token));
        emitter.onError(error -> emitters.remove(token));

        try {
            // 向客户端发送一条连接成功的事件
            emitter.send(SseEmitter.event().comment("connected"));
        } catch (IOException e) {
            // 如果发送失败,则从映射表中移除对应的token
            emitters.remove(token);
        }
        return emitter;
    }

    /**
     * 断开指定用户的SSE连接
     *
     * @param userId 用户id 用以区分不同用户的连接
     * @param token  用户的唯一令牌 用以识别具体的连接
     * @author wangjiaxing
     * @date 2025/2/10 14:03
     */
    public void disconnect(Long userId, String token) {
        Map<String, SseEmitter> emitters = USER_TOKEN_EMITTERS.get(userId);
        if (emitters == null || emitters.isEmpty()) {
            return;
        }
        try {
            emitters.get(token).send(SseEmitter.event().comment("disconnected"));
        } catch (Exception ignore) {
        }
        emitters.remove(token);
    }

    /**
     * 订阅SSE消息主题,并提供一个消费者函数来处理接收到的消息
     *
     * @param consumer {@link Consumer}<{@link SseMessageDTO}></> 处理SSE消息的消费者函数
     * @author wangjiaxing
     * @date 2025/2/10 14:06
     */
    public void subscribeMessage(Consumer<SseMessageDTO> consumer) {
        RedisUtils.subscribe(SSE_TOPIC, SseMessageDTO.class, consumer);
    }

    /**
     * 向指定的用户会话发送消息
     *
     * @param userId  用户id
     * @param message 消息
     * @author wangjiaxing
     * @date 2025/2/10 14:08
     */
    public void sendMessage(Long userId, String message) {
        Map<String, SseEmitter> emitters = USER_TOKEN_EMITTERS.get(userId);
        if (emitters == null || emitters.isEmpty()) {
            return;
        }
        for (Map.Entry<String, SseEmitter> entry : emitters.entrySet()) {
            try {
                entry.getValue()
                        .send(
                                SseEmitter.event()
                                        .name("message")
                                        .data(message)
                        );
            } catch (Exception e) {
                emitters.remove(entry.getKey());
            }
        }
    }

    /**
     * 向全体用户发送消息
     *
     * @param message 消息
     * @author wangjiaxing
     * @date 2025/2/10 14:12
     */
    public void sendMessage(String message) {
        for (Long userId : USER_TOKEN_EMITTERS.keySet()) {
            sendMessage(userId, message);
        }
    }

    /**
     * 发布SSE订阅消息
     *
     * @param message {@link SseMessageDTO}
     * @author wangjiaxing
     * @date 2025/2/10 14:14
     */
    public void publishMessage(SseMessageDTO message) {
        SseMessageDTO broadCastMessage = new SseMessageDTO();
        broadCastMessage.setMessage(message.getMessage());
        broadCastMessage.setUserIds(message.getUserIds());

        RedisUtils.publish(SSE_TOPIC, broadCastMessage, consumer -> {
            log.info("SSE发送主题订阅消息 topic:{} session keys:{} message:{}",
                    SSE_TOPIC, message.getUserIds(), message.getMessage());
        });
    }

    /**
     * 向所有的用户发送订阅的消息(群发)
     *
     * @param message 消息
     * @author wangjiaxing
     * @date 2025/2/10 14:17
     */
    public void publishAll(String message) {
        SseMessageDTO broadCastMessage = new SseMessageDTO();
        broadCastMessage.setMessage(message);
        RedisUtils.publish(SSE_TOPIC, broadCastMessage, consumer -> {
            log.info("SSE发送主题订阅消息 topic:{} message:{}", SSE_TOPIC, message);
        });
    }
}
