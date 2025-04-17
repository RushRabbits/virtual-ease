package com.awake.ve.common.mybatis.handler;


import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.core.utils.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * json 基本数据类型列表 处理程序
 * <br>
 * 用于处理基本数据类型包装类的数组
 *
 * @author dl
 * @date 2024/9/5 18:25
 */
@Slf4j
@MappedTypes({Object.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class JsonBasicTypeListHandler<T> extends AbstractJsonTypeHandler<List<T>> {
    // BasicTypeList
    private static final ObjectMapper OBJECT_MAPPER = SpringUtils.getBean(ObjectMapper.class);

    public JsonBasicTypeListHandler(Class<?> type) {
        super(type);
    }

    public JsonBasicTypeListHandler(Class<?> type, Field field) {
        super(type, field);
    }

    /**
     * 反序列化json
     *
     * @param json json字符串
     * @return T
     */
    @Override
    public List<T> parse(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(json, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            log.error("[JsonBasicTypeListHandler][parse] json转换异常 obj={}", json, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 序列化json
     *
     * @param obj 对象信息
     * @return json字符串
     */
    @Override
    public String toJson(List<T> obj) {
        if (CollectionUtils.isEmpty(obj)) {
            return null;
        }
        // 判断泛型是否是数字
        if (obj.get(0) instanceof Number) {
            return obj.toString();
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("[JsonBasicTypeListHandler][toJson] json转换异常 obj={}", obj, e);
            throw new RuntimeException(e);
        }
    }
}
