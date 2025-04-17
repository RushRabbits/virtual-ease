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
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.*;

/**
 * mysql查询结果解密拦截器
 *
 * @author wangjiaxing
 * @date 2025/2/11 9:27
 */
@Slf4j
@AllArgsConstructor
@Intercepts({@Signature(
        type = ResultHandler.class,
        method = "handleResultSets",
        args = {Statement.class}
)})
public class MybatisDecryptInterceptor implements Interceptor {

    private final EncryptorManager encryptorManager;
    private final EncryptProperties defaultProperties;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取执行的mysql结果
        Object result = invocation.proceed();
        if (result == null) {
            return null;
        }
        decryptHandler(result);
        return result;
    }

    /**
     * 解密对象
     *
     * @param result 待解密对象
     */
    private void decryptHandler(Object result) {
        if (Objects.isNull(result)) {
            return;
        }
        if (result instanceof Map<?, ?> map) {
            new HashSet<>(map.values()).forEach(this::decryptHandler);
            return;
        }
        if (result instanceof List<?> list) {
            if (CollectionUtils.isEmpty(list)) {
                return;
            }
            // 第一个元素是否有注解,如果没有则直接返回,提高效率
            Object firstItem = list.get(0);
            if (firstItem == null || CollectionUtils.isEmpty(encryptorManager.getFieldCache(firstItem.getClass()))) {
                return;
            }
            list.forEach(this::decryptHandler);
        }

        // 不存在于缓存中的类,就是没有加密注解的类(也可能是typeAliasesPackage写错)
        Set<Field> fieldSet = encryptorManager.getFieldCache(result.getClass());
        if (CollectionUtils.isEmpty(fieldSet)) {
            return;
        }
        try {
            for (Field field : fieldSet) {
                field.set(result, this.decryptField(Convert.toStr(field.get(result)), field));
            }
        } catch (Exception e) {
            log.error("[MybatisDecryptInterceptor][decryptHandler] 处理解密字段时出错", e);
        }
    }

    /**
     * 字段解密
     *
     * @param str   待解密字符串
     * @param field {@link Field}
     * @return 解密后的字符串
     * @author wangjiaxing
     * @date 2025/2/11 9:19
     */
    private String decryptField(String str, Field field) {
        if (Objects.isNull(str)) {
            return null;
        }
        EncryptField encryptField = field.getAnnotation(EncryptField.class);
        EncryptContext context = new EncryptContext();
        context.setAlgorithm(encryptField.algorithm() == AlgorithmType.DEFAULT ? defaultProperties.getAlgorithm() : encryptField.algorithm());
        context.setPassword(StringUtils.isBlank(encryptField.password()) ? defaultProperties.getPassword() : encryptField.password());
        context.setEncode(encryptField.encode() == EncodeType.DEFAULT ? defaultProperties.getEncode() : encryptField.encode());
        context.setPrivateKey(StringUtils.isBlank(encryptField.privateKey()) ? defaultProperties.getPrivateKey() : encryptField.privateKey());
        context.setPublicKey(StringUtils.isBlank(encryptField.publicKey()) ? defaultProperties.getPublicKey() : encryptField.publicKey());
        return this.encryptorManager.decrypt(str, context);
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
