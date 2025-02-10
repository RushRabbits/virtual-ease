package com.awake.ve.common.sse.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.awake.ve.common.core.domain.R;
import com.awake.ve.common.satoken.utils.LoginHelper;
import com.awake.ve.common.sse.core.SseEmitterManager;
import com.awake.ve.common.sse.dto.SseMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@ConditionalOnProperty(value = "sse.enabled", havingValue = "true")
@RequiredArgsConstructor
public class SseController implements DisposableBean {

    private final SseEmitterManager sseEmitterManager;

    /**
     * 建立SSE连接
     * TEXT_EVENT_STREAM_VALUE = "text/event-stream"
     * 这个媒体类型告诉浏览器这是一个SSE连接
     * 使用这个媒体类型后，浏览器会保持连接打开状态
     *
     * @return {@link SseEmitter}
     * @author wangjiaxing
     * @date 2025/2/10 14:30
     */
    @GetMapping(value = "${sse.path}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect() {
        String token = StpUtil.getTokenValue();
        Long userId = LoginHelper.getUserId();
        return sseEmitterManager.connect(userId, token);
    }

    /**
     * 关闭SSE连接
     *
     * @author wangjiaxing
     * @date 2025/2/10 14:34
     */
    @SaIgnore
    @GetMapping("${sse.path}/close")
    public R<Void> close() {
        String token = StpUtil.getTokenValue();
        Long userId = LoginHelper.getUserId();
        sseEmitterManager.disconnect(userId, token);
        return R.ok();
    }

    /**
     * 向特定用户发送消息
     *
     * @param userId  用户id
     * @param message 消息
     * @author wangjiaxing
     * @date 2025/2/10 14:35
     */
    @GetMapping("${sse.path}/send")
    public R<Void> send(Long userId, String message) {
        SseMessageDTO dto = new SseMessageDTO();
        dto.setUserIds(List.of(userId));
        dto.setMessage(message);
        sseEmitterManager.publishMessage(dto);
        return R.ok();
    }

    /**
     * 向全体用户发送消息
     *
     * @param message 消息
     * @author wangjiaxing
     * @date 2025/2/10 14:38
     */
    @GetMapping("${sse.path}/sendAll")
    public R<Void> send(String message) {
        sseEmitterManager.publishAll(message);
        return R.ok();
    }

    /**
     * @author wangjiaxing
     * @date 2025/2/10 14:39
     */
    @Override
    public void destroy() {
        // 销毁时不需要做什么,此方法是避免无用操作报错
    }
}
