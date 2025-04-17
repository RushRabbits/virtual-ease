package com.awake.ve.common.sms.config;

import com.awake.ve.common.sms.core.dao.PlusSmsDao;
import com.awake.ve.common.sms.handler.SmsExceptionHandler;
import org.dromara.sms4j.api.dao.SmsDao;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 短信配置类
 *
 * @author wangjiaxing
 * @date 2025/2/11 10:39
 */
@AutoConfiguration(after = {RedisAutoConfiguration.class})
public class SmsAutoConfiguration {

    @Bean
    @Primary
    public SmsDao smsDao() {
        return new PlusSmsDao();
    }

    @Bean
    public SmsExceptionHandler smsExceptionHandler() {
        return new SmsExceptionHandler();
    }
}
