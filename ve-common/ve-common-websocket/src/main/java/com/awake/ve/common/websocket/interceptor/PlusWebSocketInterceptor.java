package com.awake.ve.common.websocket.interceptor;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import com.awake.ve.common.core.domain.model.LoginUser;
import com.awake.ve.common.core.utils.ServletUtils;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.satoken.utils.LoginHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

import static com.awake.ve.common.websocket.constant.WebSocketConstants.LOGIN_USER_KEY;

/**
 * WebSocket握手请求的拦截器
 *
 * @author wangjiaxing
 * @date 2025/2/10 16:11
 */
@Slf4j
public class PlusWebSocketInterceptor implements HandshakeInterceptor {

    /**
     * WebSocket握手之前执行的前置处理方法
     *
     * @param request          {@link ServerHttpRequest}
     * @param response         {@link ServerHttpResponse}
     * @param webSocketHandler {@link WebSocketHandler}
     * @param attributes       {@link Map}
     * @return 允许握手继续进行, 返回true, 否则返回false
     * @author wangjiaxing
     * @date 2025/2/10 16:11
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {
        try {
            // 检查是否登录
            LoginUser loginUser = LoginHelper.getLoginUser();
            if (loginUser == null) {
                return false;
            }

            // 解决ws不走mvc拦截器的问题
            // 检查header以及param里的clientId与token里的是否一致
            String headerCid = ServletUtils.getRequest().getHeader(LoginHelper.CLIENT_KEY);
            String paramCid = ServletUtils.getRequest().getParameter(LoginHelper.CLIENT_KEY);
            String clientId = StpUtil.getExtra(LoginHelper.CLIENT_KEY).toString();

            if (!StringUtils.equalsAny(headerCid, paramCid, clientId)) {
                throw NotLoginException.newInstance(StpUtil.getLoginType(),
                        "-100",
                        "客户端id与token不匹配",
                        StpUtil.getTokenValue());
            }

            attributes.put(LOGIN_USER_KEY, loginUser);
            return true;
        } catch (NotLoginException e) {
            log.error("[PlusWebSocketInterceptor][beforeHandshake] WebSocket握手失败,无法访问系统资源, 请检查token是否正确", e);
            return false;
        }
    }

    /**
     * WebSocket握手成功后执行的后置处理方法
     *
     * @param request   WebSocket握手请求
     * @param response  WebSocket握手响应
     * @param wsHandler WebSocket处理程序
     * @param exception 握手过程中可能出现的异常
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // 在这个方法中可以执行一些握手成功后的后续处理逻辑，比如记录日志或者其他操作
    }
}
