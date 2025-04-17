package com.awake.ve.common.sms.handler;

import cn.hutool.http.HttpStatus;
import com.awake.ve.common.core.domain.R;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.dromara.sms4j.comm.exception.SmsBlendException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * SMS异常处理器
 *
 * @author wangjiaxing
 * @date 2025/2/11 10:47
 */
@Slf4j
@RestControllerAdvice
public class SmsExceptionHandler {

    /**
     * 处理SmsBlendException异常
     */
    @ExceptionHandler(SmsBlendException.class)
    public R<Void> handleSmsBlendException(SmsBlendException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("[SmsExceptionHandler][handleSmsBlendException] 请求地址'{}',短信发送异常", requestURI);
        return R.fail(HttpStatus.HTTP_INTERNAL_ERROR, "短信发送失败,请稍后重试");
    }
}
