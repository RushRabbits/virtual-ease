package com.awake.ve.common.json.handler;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.ser.std.NumberSerializer;

import java.io.IOException;

/**
 * 超出JS最大精度的数字处理
 *
 * @author wangjiaxing
 * @date 2024/12/17 9:41
 */
@JacksonStdImpl
public class BigNumberSerializer extends NumberSerializer {

    /**
     * <a href="https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Number/isSafeInteger">...</a>
     * 根据JS的 Number.MAX_SAFE_INTEGER和Number.MIN_SAFE_INTEGER，判断是否是安全的数字
     */
    private static final long MAX_SAFE_INTEGER = 9007199254740991L;
    private static final long MIN_SAFE_INTEGER = -9007199254740991L;

    public static final BigNumberSerializer INSTANCE = new BigNumberSerializer(Number.class);

    public BigNumberSerializer(Class<? extends Number> rawType) {
        super(rawType);
    }

    @Override
    public void serialize(Number value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        if (value.longValue() > MAX_SAFE_INTEGER || value.longValue() < MIN_SAFE_INTEGER) {
            super.serialize(value, generator, provider);
        } else {
            generator.writeString(value.toString());
        }
    }
}
