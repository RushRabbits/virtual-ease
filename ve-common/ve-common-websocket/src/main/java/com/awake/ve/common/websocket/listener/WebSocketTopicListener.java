package com.awake.ve.common.websocket.listener;

import com.awake.ve.common.websocket.holder.WebSocketSessionHolder;
import com.awake.ve.common.websocket.utils.WebSocketUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.util.CollectionUtils;

/**
 * WebSocket 主题订阅监听器
 *
 * @author wangjiaxing
 * @date 2025/2/10 16:21
 */
@Slf4j
public class WebSocketTopicListener implements ApplicationRunner, Ordered {

    /**
     * 项目启动时初始化WebSocket主题订阅监听器
     *
     * @param args 应用程序参数
     * @author wangjiaxing
     * @date 2025/2/10 16:21
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        WebSocketUtils.subscribeMessage(message -> {
            log.info("WebSocket主题订阅收到消息 session keys = {} message = {}", message.getSessionKeys(), message.getMessage());
            // 如果key不为空就按照key发消息,否则就群发
            if (!CollectionUtils.isEmpty(message.getSessionKeys())) {
                for (Long sessionKey : message.getSessionKeys()) {
                    if (WebSocketSessionHolder.existSession(sessionKey)) {
                        WebSocketUtils.sendMessage(sessionKey, message.getMessage());
                    }
                }
            } else {
                for (Long sessionKey : WebSocketSessionHolder.getSessionAll()) {
                    WebSocketUtils.sendMessage(sessionKey, message.getMessage());
                }
            }
        });
        log.info("初始化WebSocket主题订阅监听器成功");
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
