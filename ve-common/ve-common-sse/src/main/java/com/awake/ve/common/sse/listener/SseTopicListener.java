package com.awake.ve.common.sse.listener;

import com.awake.ve.common.sse.core.SseEmitterManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.util.CollectionUtils;

/**
 * SSE主题订阅监听器
 *
 * @author wangjiaxing
 * @date 2025/2/10 14:20
 */
@Slf4j
public class SseTopicListener implements ApplicationRunner, Ordered {

    @Autowired
    private SseEmitterManager sseEmitterManager;

    /**
     * 项目启动时,初始化SSE主题订阅监听器
     *
     * @param args 应用程序参数
     * @author wangjiaxing
     * @date 2025/2/10 14:21
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        sseEmitterManager.subscribeMessage(message -> {
            log.info("SSE主题订阅收到消息 session keys = {} message ={}", message.getUserIds(), message.getMessage());
            // 如果key不为空,则按照key发送消息,否则就群发
            if (CollectionUtils.isEmpty(message.getUserIds())) {
                sseEmitterManager.sendMessage(message.getMessage());
            } else {
                for (Long userId : message.getUserIds()) {
                    sseEmitterManager.sendMessage(userId, message.getMessage());
                }
            }
        });
        log.info("SSE主题订阅监听器初始化完成");
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
