package com.awake.ve.common.websocket.holder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocketSessionHolder 保存当前所有在线的会话消息
 *
 * @author wangjiaxing
 * @date 2025/2/10 15:30
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebSocketSessionHolder {

    private static final Map<Long, WebSocketSession> USER_SESSION_MAP = new ConcurrentHashMap<>();

    /**
     * 添加会话
     *
     * @param sessionKey sessionKey
     * @param session    {@link WebSocketSession}
     * @author wangjiaxing
     * @date 2025/2/10 15:32
     */
    public static void addSession(Long sessionKey, WebSocketSession session) {
        USER_SESSION_MAP.put(sessionKey, session);
    }

    /**
     * 移除会话
     *
     * @param sessionKey sessionKey
     * @author wangjiaxing
     * @date 2025/2/10 15:33
     */
    public static void removeSession(Long sessionKey) {
        if (USER_SESSION_MAP.containsKey(sessionKey)) {
            USER_SESSION_MAP.remove(sessionKey);
        }
    }

    /**
     * 获取指定的会话
     *
     * @param sessionKey sessionKey
     * @author wangjiaxing
     * @date 2025/2/10 15:35
     */
    public static WebSocketSession getSessions(Long sessionKey) {
        return USER_SESSION_MAP.get(sessionKey);
    }

    /**
     * 获取所有会话的键
     *
     * @author wangjiaxing
     * @date 2025/2/10 15:36
     */
    public static Set<Long> getSessionAll() {
        return USER_SESSION_MAP.keySet();
    }

    /**
     * 会话是否存在
     *
     * @param sessionId 键
     * @author wangjiaxing
     * @date 2025/2/10 15:37
     */
    public static Boolean existSession(Long sessionId) {
        return USER_SESSION_MAP.containsKey(sessionId);
    }
}
