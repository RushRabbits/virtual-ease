package com.awake.ve.common.websocket.handler;

import com.awake.ve.common.core.domain.model.LoginUser;
import com.awake.ve.common.websocket.dto.WebSocketMessageDTO;
import com.awake.ve.common.websocket.holder.WebSocketSessionHolder;
import com.awake.ve.common.websocket.utils.WebSocketUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.List;
import java.util.Objects;

import static com.awake.ve.common.websocket.constant.WebSocketConstants.LOGIN_USER_KEY;

/**
 * WebSocketHandler实现类
 *
 * @author wangjiaxing
 * @date 2025/2/10 15:25
 */
@Slf4j
public class PlusWebSocketHandler extends AbstractWebSocketHandler {

    /**
     * websocket连接建立成功后
     *
     * @param session {@link WebSocketSession}
     * @author wangjiaxing
     * @date 2025/2/10 15:25
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LoginUser loginUser = (LoginUser) session.getAttributes().get(LOGIN_USER_KEY);
        if (Objects.isNull(loginUser)) {
            session.close(CloseStatus.BAD_DATA);
            log.info("[connect] 无效的token. sessionId:{}", session.getId());
            return;
        }
        WebSocketSessionHolder.addSession(loginUser.getUserId(), session);
        log.info("[connect] sessionId:{} , userId:{} , userType:{}", session.getId(), loginUser.getUserId(), loginUser.getUserType());
    }

    /**
     * 处理接收到的文本信息
     *
     * @param session websocket会话
     * @param message {@link TextMessage}
     * @author wangjiaxing
     * @date 2025/2/10 15:41
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 从websocket会话中获取用户信息
        LoginUser loginUser = (LoginUser) session.getAttributes().get(LOGIN_USER_KEY);

        // 创建WebSocketMessageDTO
        WebSocketMessageDTO messageDTO = new WebSocketMessageDTO();
        messageDTO.setSessionKeys(List.of(loginUser.getUserId()));
        messageDTO.setMessage(message.getPayload());
        WebSocketUtils.publishMessage(messageDTO);
    }

    /**
     * 处理接收到的二进制信息
     *
     * @param session {@link WebSocketSession}
     * @param message {@link BinaryMessage}
     * @author wangjiaxing
     * @date 2025/2/10 16:01
     */
    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        super.handleBinaryMessage(session, message);
    }

    /**
     * 处理接收到的Pong消息(心跳监测)
     *
     * @param session {@link WebSocketSession}
     * @param message {@link PongMessage}
     * @author wangjiaxing
     * @date 2025/2/10 16:03
     */
    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        WebSocketUtils.sendPongMessage(session, message);
    }

    /**
     * 处理websocket传输错误
     *
     * @param session   {@link WebSocketSession}
     * @param exception {@link Throwable}
     * @author wangjiaxing
     * @date 2025/2/10 16:04
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("[PlusWebSocketHandler][handleTransportError] session:{} , error:{}", session, exception.getMessage());
    }

    /**
     * websocket会话关闭后,执行清理操作
     *
     * @param session {@link WebSocketSession}
     * @param status  {@link CloseStatus}
     * @author wangjiaxing
     * @date 2025/2/10 16:07
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        LoginUser loginUser = (LoginUser) session.getAttributes().get(LOGIN_USER_KEY);
        if (Objects.isNull(loginUser)) {
            log.info("[PlusWebSocketHandler][afterConnectionClosed] 无效的token sessionId:{}", session.getId());
            return;
        }
        WebSocketSessionHolder.removeSession(loginUser.getUserId());
        log.info("[PlusWebSocketHandler][afterConnectionClosed] sessionId:{} , userId:{} , userType:{}", session.getId(), loginUser.getUserId(), loginUser.getUserType());
    }
    /**
     * 指示处理程序是否支持接收部分消息
     *
     * @return 如果支持接收部分消息，则返回true；否则返回false
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
