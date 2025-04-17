package com.awake.ve.common.encrypt.interceptor;

import cn.hutool.core.convert.Convert;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.encrypt.annotation.EncryptField;
import com.awake.ve.common.encrypt.core.EncryptContext;
import com.awake.ve.common.encrypt.core.EncryptorManager;
import com.awake.ve.common.encrypt.enums.AlgorithmType;
import com.awake.ve.common.encrypt.enums.EncodeType;
import com.awake.ve.common.encrypt.properties.EncryptProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * mysql落库之前的参数拦截器
 *
 * @author wangjiaxing
 * @date 2025/2/11 9:30
 */
@Slf4j
@Intercepts({
        @Signature(
                type = ParameterHandler.class,
                method = "setParameters",
                args = {java.sql.PreparedStatement.class}
        )
})
@AllArgsConstructor
public class MybatisEncryptInterceptor implements Interceptor {

    private final EncryptorManager encryptorManager;
    private final EncryptProperties encryptProperties;


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return invocation;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof ParameterHandler parameterHandler) {
            // 进行加密操作
            Object parameterObject = parameterHandler.getParameterObject();
            if (!Objects.isNull(parameterObject) && !(parameterObject instanceof String)) {
                this.encryptHandler(parameterObject);
            }
        }
        return target;
    }

    /**
     * 对象加密
     *
     * @param parameterObject 参数对象
     * @author wangjiaxing
     * @date 2025/2/11 9:36
     */
    private void encryptHandler(Object parameterObject) {
        if (Objects.isNull(parameterObject)) {
            return;
        }
        if (parameterObject instanceof Map<?, ?> map) {
            new HashSet<>(map.values()).forEach(this::encryptHandler);
            return;
        }
        if (parameterObject instanceof List<?> list) {
            if (CollectionUtils.isEmpty(list)) {
                return;
            }
            // 判断第一个元素是否含有注解,如果没有则直接返回,提高效率
            Object firstItem = list.get(0);
            if (firstItem == null || CollectionUtils.isEmpty(encryptorManager.getFieldCache(firstItem.getClass()))) {
                return;
            }
            list.forEach(this::encryptHandler);
            return;
        }

        // 不存在于缓存中的类,就是没有加密注解标注的类(也可能是typeAliasesPackage写错)
        Set<Field> fieldSet = encryptorManager.getFieldCache(parameterObject.getClass());
        if (CollectionUtils.isEmpty(fieldSet)) {
            return;
        }
        try {
            for (Field field : fieldSet) {
                field.set(parameterObject, this.encryptField(Convert.toStr(field.get(parameterObject)), field));
            }
        } catch (Exception e) {
            log.error("[MybatisEncryptInterceptor][encryptHandler] 处理加密字段时出错", e);
        }
    }

    /**
     * 字段加密
     *
     * @param str   待加密的值
     * @param field 待加密字段
     * @return 加密后的值
     * @author wangjiaxing
     * @date 2025/2/11 9:43
     */
    private String encryptField(String str, Field field) {
        if (Objects.isNull(str)) {
            return null;
        }
        EncryptField encryptField = field.getAnnotation(EncryptField.class);
        EncryptContext context = new EncryptContext();
        context.setAlgorithm(encryptField.algorithm() == AlgorithmType.DEFAULT ? encryptProperties.getAlgorithm() : encryptField.algorithm());
        context.setEncode(encryptField.encode() == EncodeType.DEFAULT ? encryptProperties.getEncode() : encryptField.encode());
        context.setPassword(StringUtils.isBlank(encryptField.password()) ? encryptProperties.getPassword() : encryptField.password());
        context.setPrivateKey(StringUtils.isBlank(encryptField.privateKey()) ? encryptProperties.getPrivateKey() : encryptField.privateKey());
        context.setPublicKey(StringUtils.isBlank(encryptField.publicKey()) ? encryptProperties.getPublicKey() : encryptField.publicKey());
        return this.encryptorManager.encrypt(str, context);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
