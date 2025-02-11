package com.awake.ve.common.encrypt.core;

import cn.hutool.core.util.ReflectUtil;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.encrypt.annotation.EncryptField;
import com.awake.ve.common.encrypt.core.encryptor.AbstractEncryptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 加密管理类
 *
 * @author wangjiaxing
 * @date 2025/2/10 18:39
 */
@Slf4j
public class EncryptorManager {

    /**
     * 存储不同类型的加密器
     */
    Map<Integer, IEncryptor> encryptorCache = new ConcurrentHashMap<>();

    /**
     * 存储类和需要加密的字段的映射关系
     */
    Map<Class<?>, Set<Field>> fieldCache = new ConcurrentHashMap<>();

    /**
     * @param typeAliasesPackage 需要扫描的实体类存放的包
     * @author wangjiaxing
     * @date 2025/2/10 18:42
     */
    public EncryptorManager(String typeAliasesPackage) {
        scanEncryptClasses(typeAliasesPackage);
    }

    /**
     * 获取指定类所有需要加密的字段集
     *
     * @author wangjiaxing
     * @date 2025/2/10 19:08
     */
    public Set<Field> getFieldCache(Class<?> sourceClazz) {
        return CollectionUtils.isEmpty(fieldCache.get(sourceClazz)) ? null : fieldCache.get(sourceClazz);
    }

    /**
     * 注册加密器并获取加密器
     *
     * @param encryptContext {@link EncryptContext}
     * @return {@link IEncryptor}
     * @author wangjiaxing
     * @date 2025/2/10 19:09
     */
    public IEncryptor registerAndGetIEncryptor(EncryptContext encryptContext) {
        int key = encryptContext.hashCode();
        if (encryptorCache.containsKey(key)) {
            return encryptorCache.get(key);
        }
        AbstractEncryptor encryptor = ReflectUtil.newInstance(encryptContext.getAlgorithm().getClazz(), encryptContext);
        encryptorCache.put(key, encryptor);
        return encryptor;
    }

    /**
     * 移除加密器
     *
     * @param encryptContext {@link EncryptContext}
     * @author wangjiaxing
     * @date 2025/2/10 19:14
     */
    public void removeEncryptor(EncryptContext encryptContext) {
        this.encryptorCache.remove(encryptContext.hashCode());
    }

    /**
     * 加密
     *
     * @param value          待加密的值
     * @param encryptContext {@link EncryptContext}
     * @return 加密后的值
     * @author wangjiaxing
     * @date 2025/2/10 19:15
     */
    public String encrypt(String value, EncryptContext encryptContext) {
        IEncryptor encryptor = this.registerAndGetIEncryptor(encryptContext);
        return encryptor.encrypt(value, encryptContext.getEncode());
    }

    /**
     * 解密
     *
     * @param value          待解密的值
     * @param encryptContext {@link EncryptContext}
     * @return 解密后的值
     * @author wangjiaxing
     * @date 2025/2/10 19:17
     */
    public String decrypt(String value, EncryptContext encryptContext) {
        IEncryptor encryptor = this.registerAndGetIEncryptor(encryptContext);
        return encryptor.decrypt(value);
    }

    /**
     * 通过 typeAliasesPackage 设置的扫描包 扫描缓存实体
     */
    private void scanEncryptClasses(String typeAliasesPackage) {
        // 资源解析器
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 元数据读取工厂
        CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory();

        // 处理包路径
        String[] packagePatternArray = StringUtils.splitPreserveAllTokens(typeAliasesPackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
        String classPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX;

        try {
            // 扫描每个包下的类
            for (String packagePattern : packagePatternArray) {
                String path = ClassUtils.convertClassNameToResourcePath(packagePattern);
                Resource[] resources = resolver.getResources(classPath + path + "/*.class");
                for (Resource resource : resources) {
                    ClassMetadata classMetadata = factory.getMetadataReader(resource).getClassMetadata();
                    Class<?> clazz = Resources.classForName(classMetadata.getClassName());
                    Set<Field> fieldSet = getEncryptFieldSetFromClass(clazz);
                    if (!CollectionUtils.isEmpty(fieldSet)) {
                        fieldCache.put(clazz, fieldSet);
                    }
                }
            }
        } catch (Exception e) {
            log.error("初始化数据安全缓存时出错", e);
        }
    }

    /**
     * 获取类中需要加密的字段集
     *
     * @param clazz {@link Class}
     * @return 类中需要加密的字段集
     * @author wangjiaxing
     * @date 2025/2/10 18:49
     */
    private Set<Field> getEncryptFieldSetFromClass(Class<?> clazz) {
        Set<Field> fieldSet = new HashSet<>();

        // 如果clazz是接口,内部类,匿名类,则直接返回
        if (clazz.isInterface() || clazz.isMemberClass() || clazz.isAnonymousClass()) {
            return fieldSet;
        }

        // 递归获取包括父类在内的所有字段
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            fieldSet.addAll(Arrays.asList(fields));
            clazz = clazz.getSuperclass();
        }

        if (CollectionUtils.isEmpty(fieldSet)) {
            return fieldSet;
        }

        // 筛选带有@EncryptField注解且类型为String的字段
        fieldSet = fieldSet.stream().filter(field -> field.isAnnotationPresent(EncryptField.class) && field.getType() == String.class)
                .collect(Collectors.toSet());

        // 设置字段可访问
        fieldSet.forEach(field -> field.setAccessible(true));

        return fieldSet;
    }
}
