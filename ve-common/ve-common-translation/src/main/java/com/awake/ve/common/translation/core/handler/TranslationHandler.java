package com.awake.ve.common.translation.core.handler;

import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.core.utils.reflect.ReflectUtils;
import com.awake.ve.common.translation.annotation.Translation;
import com.awake.ve.common.translation.core.TranslationInterface;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class TranslationHandler extends JsonSerializer<Object> implements ContextualSerializer {

    /**
     * 全部的翻译实现类的集合(映射器集合)
     */
    public static final Map<String, TranslationInterface<?>> TRANSLATION_MAPPER = new ConcurrentHashMap<>();

    private Translation translation;

    @Override
    public void serialize(Object value, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
        TranslationInterface<?> trans = TRANSLATION_MAPPER.get(translation.type());
        if (!Objects.isNull(trans)) {
            if (StringUtils.isNotBlank(translation.mapper())) {
                value = ReflectUtils.invokeGetter(generator.getCurrentValue(), translation.mapper());
            }
            if (Objects.isNull(value)) {
                generator.writeNull();
                return;
            }
            Object result = trans.translation(value, translation.other());
            generator.writeObject(result);
        } else {
            generator.writeObject(value);
        }

    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        return null;
    }
}
