package com.awake.ve.common.sensitive.handler;

import cn.hutool.core.util.ObjectUtil;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.sensitive.annotation.Sensitive;
import com.awake.ve.common.sensitive.core.SensitiveService;
import com.awake.ve.common.sensitive.core.SensitiveStrategy;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;

import java.io.IOException;
import java.util.Objects;

/**
 * 数据脱敏的json序列化工具
 *
 * @author wangjiaxing
 * @date 2025/2/8 14:37
 */
@Slf4j
public class SensitiveHandler extends JsonSerializer<String> implements ContextualSerializer {

    private SensitiveStrategy strategy;
    private String roleKey;
    private String perms;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializer) throws IOException {
        try {
            SensitiveService service = SpringUtils.getBean(SensitiveService.class);
            if (ObjectUtil.isNotNull(service) && service.isSensitive(roleKey, perms)) {
                gen.writeString(strategy.desensitizer().apply(value));
            } else {
                gen.writeString(value);
            }
        } catch (BeansException e) {
            log.error("数据脱敏实现不存在 , 采用默认处理 => {}", e.getMessage());
            gen.writeString(value);
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializer, BeanProperty beanProperty) throws JsonMappingException {
        Sensitive sensitive = beanProperty.getAnnotation(Sensitive.class);
        if (Objects.nonNull(sensitive) && Objects.equals(String.class, beanProperty.getType().getRawClass())) {
            this.strategy = sensitive.strategy();
            this.roleKey = sensitive.roleKey();
            this.perms = sensitive.perms();
            return this;
        }
        return serializer.findValueSerializer(beanProperty.getType(), beanProperty);
    }
}
