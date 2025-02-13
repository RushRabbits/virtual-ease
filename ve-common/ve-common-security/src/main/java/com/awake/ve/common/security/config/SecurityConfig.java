package com.awake.ve.common.security.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.httpauth.basic.SaHttpBasicUtil;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.awake.ve.common.core.constant.HttpStatus;
import com.awake.ve.common.core.exception.SseException;
import com.awake.ve.common.core.utils.ServletUtils;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.satoken.utils.LoginHelper;
import com.awake.ve.common.security.config.properties.SecurityProperties;
import com.awake.ve.common.security.handler.AllUrlHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 权限安全配置
 *
 * @author wangjiaxing
 * @date 2025/2/10 11:18
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(SecurityProperties.class)
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

    private final SecurityProperties securityProperties;

    /**
     * 注册sa-token拦截器
     *
     * @param registry {@link InterceptorRegistry}
     * @author wangjiaxing
     * @date 2025/2/10 11:20
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handler -> {
                    AllUrlHandler allUrlHandler = SpringUtils.getBean(AllUrlHandler.class);
                    // 登录验证
                    SaRouter
                            // 获取所有的
                            .match(allUrlHandler.getUrls())
                            // 对未排除的路径进行检查
                            .check(() -> {
                                HttpServletRequest request = ServletUtils.getRequest();
                                // 检查是否登录,是否有token
                                try {
                                    StpUtil.checkLogin();
                                } catch (NotLoginException e) {
                                    if (request.getRequestURI().contains("sse")) {
                                        throw new SseException(e.getMessage(), e.getCode());
                                    } else {
                                        throw e;
                                    }
                                }

                                // 检查header与param里的clientId与token里的是否一致
                                String headerCid = request.getHeader(LoginHelper.CLIENT_KEY);
                                String paramId = ServletUtils.getParameter(LoginHelper.CLIENT_KEY);
                                String clientId = StpUtil.getExtra(LoginHelper.CLIENT_KEY).toString();

                                if (!StringUtils.equalsAny(headerCid, paramId, clientId)) {
                                    log.info("requestURI:{} , headerCid = {}", request.getRequestURI(),headerCid);
                                    log.info("requestURI:{} , paramId = {}" , request.getRequestURI(), paramId);
                                    log.info("requestURI:{} , clientId = {}" ,request.getRequestURI(), clientId);
                                    // token无效
                                    throw NotLoginException.newInstance(
                                            StpUtil.getLoginType(),
                                            "-100", "客户端ID与token不匹配",
                                            StpUtil.getTokenValue());
                                }
                            });
                })).addPathPatterns("/**")
                // 排除不需要拦截的路径
                .excludePathPatterns(securityProperties.getExcludes());
    }

    /**
     * 对actuator 健康检查接口 做账号密码鉴权
     *
     * @author wangjiaxing
     * @date 2025/2/10 11:32
     */
    @Bean
    public SaServletFilter getSaServletFilter() {
        String username = SpringUtils.getProperty("spring.boot.admin.client.username");
        String password = SpringUtils.getProperty("spring.boot.admin.client.password");
        return new SaServletFilter()
                .addInclude("/actuator", "/actuator/**")
                .setAuth(obj -> SaHttpBasicUtil.check(username + ":" + password))
                .setError(e -> SaResult.error(e.getMessage()).setCode(HttpStatus.UNAUTHORIZED));
    }
}
