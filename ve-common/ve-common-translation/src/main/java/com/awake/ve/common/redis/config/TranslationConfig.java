package com.awake.ve.common.redis.config;

import com.awake.ve.common.redis.annotation.TranslationType;
import com.awake.ve.common.redis.core.TranslationInterface;
import com.awake.ve.common.redis.core.handler.TranslationBeanSerializerModifier;
import com.awake.ve.common.redis.core.handler.TranslationHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@AutoConfiguration
public class TranslationConfig {

    @Autowired
    private List<TranslationInterface<?>> list;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        Map<String, TranslationInterface<?>> map = new HashMap<>(list.size());

        for (TranslationInterface<?> trans : list) {
            if (trans.getClass().isAnnotationPresent(TranslationType.class)) {
                TranslationType annotation = trans.getClass().getAnnotation(TranslationType.class);
                map.put(annotation.type(), trans);
            } else {
                log.warn(trans.getClass().getName() + "未找到翻译的实现类,翻译实现类未标注 TranslationType 注解", trans.getClass().getName());
            }
        }
        TranslationHandler.TRANSLATION_MAPPER.putAll(map);

        // 设置 Bean 序列化修改器
        objectMapper.setSerializerFactory(
                objectMapper.getSerializerFactory()
                        .withSerializerModifier(new TranslationBeanSerializerModifier())
        );
    }
}
