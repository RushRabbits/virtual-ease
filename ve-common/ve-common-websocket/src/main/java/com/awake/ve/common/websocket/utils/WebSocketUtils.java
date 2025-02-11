package com.awake.ve.common.websocket.utils;

import com.awake.ve.common.translation.utils.RedisUtils;
import com.awake.ve.common.websocket.dto.WebSocketMessageDTO;
import com.awake.ve.common.websocket.holder.WebSocketSessionHolder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.awake.ve.common.websocket.constant.WebSocketConstants.WEB_SOCKET_TOPIC;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebSocketUtils {

    /**
     * 向指定的websocket会话发送消息
     *
     * @param sessionKey 键
     * @param message    消息
     * @author wangjiaxing
     * @date 2025/2/10 15:45
     */
    public static void sendMessage(Long sessionKey, String message) {
        WebSocketSession session = WebSocketSessionHolder.getSessions(sessionKey);
        sendMessage(session, message);
    }

    /**
     * 订阅websocket消息主题 并提供一个消费者函数来处理收到的消息
     *
     * @param consumer {@link Consumer}
     * @author wangjiaxing
     * @date 2025/2/10 15:47
     */
    public static void subscribeMessage(Consumer<WebSocketMessageDTO> consumer) {
        RedisUtils.subscribe(WEB_SOCKET_TOPIC, WebSocketMessageDTO.class, consumer);
    }

    public static void publishMessage(WebSocketMessageDTO messageDTO) {
        List<Long> unsentSessionKeys = new ArrayList<>();

        // 当前服务内的session,直接发送消息
        for (Long sessionKey : messageDTO.getSessionKeys()) {
            if (WebSocketSessionHolder.existSession(sessionKey)) {
                WebSocketUtils.sendMessage(sessionKey, messageDTO.getMessage());
                return;
            }
            unsentSessionKeys.add(sessionKey);
        }

        // 不在当前服务内的session,发布订阅消息
        if (!CollectionUtils.isEmpty(unsentSessionKeys)) {
            WebSocketMessageDTO dto = new WebSocketMessageDTO();
            dto.setMessage(messageDTO.getMessage());
            dto.setSessionKeys(unsentSessionKeys);
            RedisUtils.publish(WEB_SOCKET_TOPIC, dto, consumer -> {
                log.info("websocket消息主题发布消息 topic:{} session keys:{} message:{}",
                        WEB_SOCKET_TOPIC, unsentSessionKeys, messageDTO.getMessage());
            });
        }
    }

    /**
     * 向所有websocket会话发送消息
     *
     * @param message 消息
     * @author wangjiaxing
     * @date 2025/2/10 15:53
     */
    public static void publishAll(String message) {
        WebSocketMessageDTO broadcast = new WebSocketMessageDTO();
        broadcast.setMessage(message);
        RedisUtils.publish(WEB_SOCKET_TOPIC, broadcast, consumer -> log.info("Websocket消息主题发布消息 topic:{} message:{}", WEB_SOCKET_TOPIC, message));
    }

    /**
     * 向指定的WebSocket会话发送Pong消息
     *
     * @param session 要发送Pong消息的WebSocket会话
     */
    public static void sendPongMessage(WebSocketSession session) {
        sendMessage(session, new PongMessage());
    }

    /**
     * 向指定的WebSocket会话发送Pong消息
     *
     * @param session 要发送Pong消息的WebSocket会话
     */
    public static void sendPongMessage(WebSocketSession session, PongMessage message) {
        sendMessage(session, message);
    }

    /**
     * 向指定的WebSocket会话发送文本消息
     *
     * @param session WebSocket会话
     * @param message 要发送的文本消息内容
     */
    public static void sendMessage(WebSocketSession session, String message) {
        sendMessage(session, new TextMessage(message));
    }

    public synchronized static void sendMessage(WebSocketSession session, WebSocketMessage<?> message) {
        if (session == null || !session.isOpen()) {
            log.warn("[WebSocketUtils][sendMessage] session会话已关闭");
            return;
        }
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            log.error("[WebSocketUtils][sendMessage] session:{} 会话发送消息:{} 失败", session, message, e);
        }
    }
}
