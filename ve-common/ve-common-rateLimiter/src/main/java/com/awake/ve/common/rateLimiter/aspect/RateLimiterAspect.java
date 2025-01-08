package com.awake.ve.common.rateLimiter.aspect;

import com.awake.ve.common.core.constant.GlobalConstants;
import com.awake.ve.common.core.exception.ServiceException;
import com.awake.ve.common.core.utils.MessageUtils;
import com.awake.ve.common.core.utils.ServletUtils;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.rateLimiter.annotation.RateLimiter;
import com.awake.ve.common.rateLimiter.enums.LimitType;
import com.awake.ve.common.translation.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RateType;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.reflect.Method;

/**
 * 限流处理的切面
 *
 * @author wangjiaxing
 * @date 2025/1/8 10:21
 */
@Slf4j
@Aspect
public class RateLimiterAspect {

    /**
     * 定义spel表达式解析器
     */
    private final ExpressionParser parser = new SpelExpressionParser();

    /**
     * 轻易spel解析模板
     */
    private final ParserContext parserContext = new TemplateParserContext();

    /**
     * 方法参数解析器
     */
    private final ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();

    /**
     * 目标方法前置处理
     *
     * @author wangjiaxing
     * @date 2025/1/8 10:25
     */
    @Before("@annotation(rateLimiter)")
    public void doBefore(JoinPoint joinPoint, RateLimiter rateLimiter) {
        int time = rateLimiter.time();
        int count = rateLimiter.count();
        try {
            String combineKey = getCombineKey(rateLimiter, joinPoint);

            // 确定限流类型,默认为整体限流
            RateType rateType = RateType.OVERALL;
            if (rateLimiter.limitType() == LimitType.CLUSTER) {
                // 集群模式下按照客户端限流
                rateType = RateType.PER_CLIENT;
            }
            // redis进行限流的具体逻辑实现
            long number = RedisUtils.rateLimiter(combineKey, rateType, count, time);

            // 如果返回值为-1，则表示令牌用尽,限流
            if (number == -1) {
                String message = rateLimiter.message();
                if (StringUtils.startsWith(message, "{") && StringUtils.endsWith(message, "}")) {
                    message = MessageUtils.message(StringUtils.substring(message, 1, message.length() - 1));
                }
                throw new ServiceException(message);
            }
            log.info("限制令牌 => {} , 剩余令牌 => {} , 缓存令牌 => {}", count, number, combineKey);
        } catch (Exception e) {
            if (e instanceof ServiceException) {
                throw e;
            } else {
                throw new RuntimeException("服务器限流异常,请稍后再试");
            }
        }
    }

    /**
     * 获取combineKey
     *
     * @param rateLimiter {@link RateLimiter}
     * @param joinPoint   {@link JoinPoint}
     * @return combineKey
     * @author wangjiaxing
     * @date 2025/1/8 10:28
     */
    private String getCombineKey(RateLimiter rateLimiter, JoinPoint joinPoint) {
        String key = rateLimiter.key();
        // 判断key不为空 以及 不是表达式
        if (StringUtils.isNotBlank(key) && StringUtils.containsAny(key, "#")) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method targetMethod = signature.getMethod();
            Object[] args = joinPoint.getArgs();

            // 创建表达式计算上下文
            MethodBasedEvaluationContext context = new MethodBasedEvaluationContext(null, targetMethod, args, pnd);
            context.setBeanResolver(new BeanFactoryResolver(SpringUtils.getBeanFactory()));

            Expression expression;
            // 判断是否使用了表达式模板（例如 #{expression} 格式）
            if (StringUtils.startsWith(key, parserContext.getExpressionPrefix()) && StringUtils.endsWith(key, parserContext.getExpressionSuffix())) {
                expression = parser.parseExpression(key, parserContext);
            } else {
                // 直接的表达式（例如 #user.id 格式）
                expression = parser.parseExpression(key);
            }
            // 计算表达式的值
            key = expression.getValue(context, String.class);
        }

        StringBuilder sb = new StringBuilder(GlobalConstants.RATE_LIMIT_KEY);
        sb.append(ServletUtils.getRequest().getRequestURI()).append(":");
        if (rateLimiter.limitType() == LimitType.IP) {
            // 获取请求的ip
            sb.append(ServletUtils.getClientIP()).append(":");
        } else if (rateLimiter.limitType() == LimitType.CLUSTER) {
            // 获取客户端的实例id
            sb.append(RedisUtils.getClient().getId()).append(":");
        }
        return sb.append(key).toString();
    }
}
