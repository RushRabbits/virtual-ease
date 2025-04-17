package com.awake.ve.common.log.aspect;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.awake.ve.common.core.domain.model.LoginUser;
import com.awake.ve.common.core.utils.ServletUtils;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.json.utils.JsonUtils;
import com.awake.ve.common.log.annotation.Log;
import com.awake.ve.common.log.enums.BusinessStatus;
import com.awake.ve.common.log.event.OperaLogEvent;
import com.awake.ve.common.satoken.utils.LoginHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jodd.net.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * 日志记录切面
 *
 * @author wangjiaxing
 * @date 2024/12/17 14:21
 */
@Slf4j
@Aspect
@AutoConfiguration
public class LogAspect {

    /**
     * 排除掉的参数
     */
    public static final String[] EXCLUDE_PROPERTIES = {"password", "oldPassword", "newPassword", "confirmPassword"};

    /**
     * 计算耗时时间的线程
     */
    private static final ThreadLocal<StopWatch> TIME_THREADLOCAL = new TransmittableThreadLocal<>();

    /**
     * 执行目标方法前
     *
     * @param joinPoint     {@link JoinPoint} 连接点
     * @param controllerLog {@link Log} 日志注解
     */
    @Before(value = "@annotation(controllerLog)")
    public void doBefore(JoinPoint joinPoint, Log controllerLog) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        TIME_THREADLOCAL.set(stopWatch);
    }

    /**
     * 执行过目标方法后执行
     *
     * @param joinPoint     {@link JoinPoint} 连接点
     * @param controllerLog {@link Log} 日志注解
     * @param result        返回值
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "result")
    public void doAfterReturn(JoinPoint joinPoint, Log controllerLog, Object result) {
        handleLog(joinPoint, controllerLog, null, result);
    }

    /**
     * 目标方法执行异常后执行
     *
     * @param joinPoint     {@link JoinPoint} 连接点
     * @param controllerLog {@link Log} 日志注解
     * @param e             {@link Exception}
     * @author wangjiaxing
     * @date 2024/12/17 14:33
     */
    @AfterThrowing(pointcut = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e) {
        handleLog(joinPoint, controllerLog, e, null);
    }

    /**
     * 处理日志
     *
     * @param joinPoint     {@link JoinPoint} 连接点
     * @param controllerLog {@link Log} 日志注解
     * @param e             {@link Exception}
     * @param result        返回值
     * @author wangjiaxing
     * @date 2024/12/17 14:35
     */
    protected void handleLog(JoinPoint joinPoint, Log controllerLog, Exception e, Object result) {
        try {
            // 数据库日志
            OperaLogEvent operaLog = new OperaLogEvent();
            operaLog.setTenantId(LoginHelper.getTenantId());
            operaLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址
            String ip = ServletUtils.getClientIP();
            operaLog.setOperIp(ip);
            operaLog.setOperUrl(StringUtils.substring(ServletUtils.getRequest().getRequestURI(), 0, 255));
            LoginUser loginUser = LoginHelper.getLoginUser();
            operaLog.setOperName(loginUser.getUsername());
            operaLog.setDeptName(loginUser.getDeptName());

            if (e != null) {
                operaLog.setStatus(BusinessStatus.FAIL.ordinal());
                operaLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }

            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operaLog.setMethod(className + "." + methodName + "()");

            // 设置请求方式
            operaLog.setRequestMethod(ServletUtils.getRequest().getMethod());

            // 处理设置在注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, operaLog, result);

            // 设置耗时
            StopWatch stopWatch = TIME_THREADLOCAL.get();
            stopWatch.stop();
            operaLog.setCostTime(stopWatch.getTime());

            // 发布事件,保存数据库
            SpringUtils.context().publishEvent(operaLog);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        } finally {
            TIME_THREADLOCAL.remove();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint     {@link JoinPoint} 连接点
     * @param controllerLog {@link Log} 日志注解
     * @param operaLog      {@link OperaLogEvent}
     * @param result        返回值
     * @author wangjiaxing
     * @date 2024/12/17 14:45
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log controllerLog, OperaLogEvent operaLog, Object result) {
        // 设置action动作
        operaLog.setBusinessType(controllerLog.businessType().ordinal());
        // 设置标题
        operaLog.setTitle(controllerLog.title());
        // 设置操作人类别
        operaLog.setOperatorType(controllerLog.operatorType().ordinal());
        // 是否需要保存request参数
        if (controllerLog.isSaveRequestData()) {
            setRequestValue(joinPoint, operaLog, controllerLog.excludeParamNames());
        }
        if (controllerLog.isSaveResponseData() && Objects.nonNull(result)) {
            operaLog.setJsonResult(StringUtils.substring(JsonUtils.toJsonString(result), 0, 2000));
        }
    }

    /**
     * 设置请求值
     *
     * @param joinPoint         {@link JoinPoint} 连接点
     * @param operaLog          {@link OperaLogEvent} 日志对象
     * @param excludeParamNames 需要排除的参数
     * @author wangjiaxing
     * @date 2024/12/17 14:50
     */
    private void setRequestValue(JoinPoint joinPoint, OperaLogEvent operaLog, String[] excludeParamNames) {
        Map<String, String> paramMap = ServletUtils.getParamMap(ServletUtils.getRequest());
        String requestMethod = operaLog.getRequestMethod();
        if (MapUtil.isEmpty(paramMap) && HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs(), excludeParamNames);
            operaLog.setOperParam(StringUtils.substring(params, 0, 2000));
        } else {
            MapUtil.removeAny(paramMap, excludeParamNames);
            MapUtil.removeAny(paramMap, EXCLUDE_PROPERTIES);
            operaLog.setOperParam(StringUtils.substring(JsonUtils.toJsonString(paramMap), 0, 2000));
        }
    }

    /**
     * 拼接参数
     *
     * @param paramsArray       {@link Object[]}
     * @param excludeParamNames 排除的参数
     * @return 参数字符串
     * @author wangjiaxing
     * @date 2024/12/17 14:54
     */
    private String argsArrayToString(Object[] paramsArray, String[] excludeParamNames) {
        StringJoiner params = new StringJoiner(" ");
        if (ArrayUtil.isEmpty(paramsArray)) {
            return params.toString();
        }

        for (Object o : paramsArray) {
            if (o == null) {
                continue;
            }
            String str = JsonUtils.toJsonString(o);
            Dict dict = JsonUtils.parseMap(str);
            if (ObjectUtil.isNotNull(o) && !isFilterObject(o)) {
                MapUtil.removeAny(dict, excludeParamNames);
                MapUtil.removeAny(dict, EXCLUDE_PROPERTIES);
                str = JsonUtils.toJsonString(dict);
            }
            params.add(str);
        }

        return params.toString();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o {@link Object}
     * @return 如果是需要过滤的对象, 返回true, 否则返回false
     * @author wangjiaxing
     * @date 2024/12/17 15:00
     */
    private boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.values()) {
                return value instanceof MultipartFile;
            }
        }

        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse || o instanceof BindingResult;
    }
}
