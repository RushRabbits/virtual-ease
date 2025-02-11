package com.awake.ve.common.encrypt.filter;

import cn.hutool.core.util.ObjectUtil;
import com.awake.ve.common.core.constant.HttpStatus;
import com.awake.ve.common.core.exception.ServiceException;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.encrypt.annotation.ApiEncrypt;
import com.awake.ve.common.encrypt.properties.ApiDecryptProperties;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.util.Objects;

/**
 * Crypto过滤器
 *
 * @author wangjiaxing
 * @date 2025/2/10 17:14
 */
public class CryptoFilter implements Filter {

    private final ApiDecryptProperties properties;

    public CryptoFilter(ApiDecryptProperties properties) {
        this.properties = properties;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        // 获取加密注解
        ApiEncrypt apiEncrypt = this.getApiEncryptAnnotation(servletRequest);

        // 是否对响应加密
        boolean responseFlag = apiEncrypt != null && apiEncrypt.response();

        ServletRequest requestWrapper = null;
        ServletResponse responseWrapper = null;
        EncryptResponseBodyWrapper responseBodyWrapper = null;

        // 是否为PUT POST请求
        if (HttpMethod.PUT.matches(servletRequest.getMethod()) || HttpMethod.POST.matches(servletRequest.getMethod())) {
            // 是否存在加密请求头
            String encryptHeader = servletRequest.getHeader(properties.getHeaderFlag());
            if (StringUtils.isNotBlank(encryptHeader)) {
                // 请求解密
                requestWrapper = new DecryptRequestBodyWrapper(servletRequest, properties.getPrivateKey(), properties.getHeaderFlag());
            } else {
                // 是否有注解 有就报错 没有则放行
                if (!Objects.isNull(apiEncrypt)) {
                    HandlerExceptionResolver exceptionResolver = SpringUtils.getBean("handlerExceptionResolver", HandlerExceptionResolver.class);
                    exceptionResolver.resolveException(
                            servletRequest, servletResponse, null,
                            new ServiceException("没有访问权限,请联系管理员授权", HttpStatus.FORBIDDEN)
                    );
                    return;
                }
            }
        }

        // 判断是否对响应加密
        if (responseFlag) {
            responseBodyWrapper = new EncryptResponseBodyWrapper(servletResponse);
            responseWrapper = responseBodyWrapper;
        }

        filterChain.doFilter(
                ObjectUtil.defaultIfNull(requestWrapper, request),
                ObjectUtil.defaultIfNull(responseWrapper, response)
        );

        if (responseFlag) {
            response.reset();
            // 对原始内容加密
            String encryptContent = responseBodyWrapper.getEncryptContent(
                    servletResponse, properties.getPublicKey(), properties.getHeaderFlag()
            );
            // 对加密后的内容写出
            servletResponse.getWriter().write(encryptContent);
        }
    }

    /**
     * 获取ApiEncrypt注解
     *
     * @param servletRequest {@link HttpServletRequest}
     * @return {@link ApiEncrypt}
     * @author wangjiaxing
     * @date 2025/2/10 17:17
     */
    private ApiEncrypt getApiEncryptAnnotation(HttpServletRequest servletRequest) {
        RequestMappingHandlerMapping handlerMapping = SpringUtils.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);

        try {
            HandlerExecutionChain mappingHandler = handlerMapping.getHandler(servletRequest);
            if (Objects.isNull(mappingHandler)) {
                return null;
            }
            Object handler = mappingHandler.getHandler(); // handler不可能是null
            if (handler instanceof HandlerMethod handlerMethod) {
                return handlerMethod.getMethodAnnotation(ApiEncrypt.class);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public void destroy() {
    }
}
