package com.awake.ve.common.ssh.config;

import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.ssh.config.properties.SSHProperties;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.util.Assert;

import java.time.Duration;

import static com.awake.ve.common.ssh.enums.JschConfig.STRICT_HOST_CHECKING;

/**
 * SSHConfig配置类
 *
 * @author wangjiaxing
 * @date 2025/2/19 10:35
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties({SSHProperties.class})
@ConditionalOnProperty(prefix = "ssh", name = "enabled", havingValue = "true")
public class SSHConfig {

    private final static int CONNECT_TIME = 30000;

    /**
     * 配置JSch(单例)
     *
     * @author wangjiaxing
     * @date 2025/2/19 10:46
     */
    @Bean
    public JSch JSch() {
        return new JSch();
    }

    /**
     * jsch的session池
     *
     * @author wangjiaxing
     * @date 2025/2/19 11:37
     */
    @Bean
    public GenericObjectPool<Session> jschSessionPool(JSch jsch, SSHProperties sshProperties) {
        GenericObjectPoolConfig<Session> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(sshProperties.getMaxTotal());
        config.setMaxIdle(sshProperties.getMaxIdle());
        config.setMinIdle(sshProperties.getMinIdle());
        config.setMaxWaitMillis(sshProperties.getMaxWaitMillis());
        config.setTestOnBorrow(true);  // 借用对象时进行验证
        config.setTestOnReturn(true);  // 归还对象时进行验证
        config.setTestWhileIdle(true); // 空闲时验证对象
        config.setTimeBetweenEvictionRuns(Duration.ofMinutes(10)); // 定期检查空闲连接
        config.setJmxEnabled(false); // 关闭JMX管理

        // 创建连接池,并指定如何创建和管理Session
        return new GenericObjectPool<>(buildBasePooledObjectFactory(jsch, sshProperties), config);
    }

    @PostConstruct
    public void validateConfiguration() {
        SSHProperties sshProperties = SpringUtils.getBean(SSHProperties.class);
        Assert.hasText(sshProperties.getHost(), "SSH host信息为空");
        Assert.hasText(sshProperties.getUsername(), "SSH username信息为空");
        Assert.hasText(sshProperties.getPassword(), "SSH password信息为空");
        Assert.isTrue(sshProperties.getPort() > 0 && sshProperties.getPort() <= 65535,
                "SSH port须在0 ~ 65535之间");
    }


    /**
     * 创建BasePooledObjectFactory
     *
     * @param jsch          {@link JSch}
     * @param sshProperties {@link SSHProperties}
     * @return {@link BasePooledObjectFactory}<{@link Session}></>}
     * @author wangjiaxing
     * @date 2025/2/19 14:16
     */
    private BasePooledObjectFactory<Session> buildBasePooledObjectFactory(JSch jsch, SSHProperties sshProperties) {
        return new BasePooledObjectFactory<>() {
            /**
             * 创建session对象
             *
             * @return {@link Session}
             */
            @Override
            public Session create() throws Exception {
                log.debug("host:{} 建立新的jschSession BEGIN", sshProperties.getHost());
                try {
                    Session session = jsch.getSession(
                            sshProperties.getUsername(),
                            sshProperties.getHost(),
                            sshProperties.getPort()
                    );
                    session.setPassword(sshProperties.getPassword());
                    session.setConfig(STRICT_HOST_CHECKING.getKey(), STRICT_HOST_CHECKING.getValue());
                    session.connect(CONNECT_TIME);
                    log.debug("host:{} 建立新的jschSession END", sshProperties.getHost());
                    return session;
                } catch (Exception e) {
                    log.error("host:{} 建立新的jschSession FAILED", sshProperties.getHost(), e);
                    throw e;
                }
            }

            /**
             * 包装session对象
             *
             * @param session {@link Session}
             * @return {@link PooledObject}<{@link Session}></>
             * @author wangjiaxing
             * @date 2025/2/19 11:45
             */
            @Override
            public PooledObject<Session> wrap(Session session) {
                return new DefaultPooledObject<>(session);
            }

            /**
             * 校验session对象是否还有效
             *
             * @param pooledObject {@link Session}
             * @author wangjiaxing
             * @date 2025/2/19 11:46
             */
            @Override
            public boolean validateObject(PooledObject<Session> pooledObject) {
                Session session = pooledObject.getObject();
                return session != null && session.isConnected();
            }

            /**
             * 销毁无效的session对象
             *
             * @param pooledObject {@link PooledObject}<{@link Session}></>
             * @author wangjiaxing
             * @date 2025/2/19 11:48
             */
            @Override
            public void destroyObject(PooledObject<Session> pooledObject) throws Exception {
                Session session = pooledObject.getObject();
                if (session == null || !session.isConnected()) {
                    return;
                }
                session.disconnect();
            }

            /**
             * 连接池被借出时的操作
             *
             * @param pooledObject {@link PooledObject}<{@link Session}></>
             * @author wangjiaxing
             * @date 2025/2/19 11:55
             */
            @Override
            public void activateObject(PooledObject<Session> pooledObject) throws Exception {
                Session session = pooledObject.getObject();
                if (session == null) {
                    log.error("[SSHConfig][activateObject] session为空");
                    throw new NullPointerException("无效的session");
                }
                if (session.isConnected()) {
                    return;
                }
                session.connect(CONNECT_TIME);
            }

            @Override
            public void passivateObject(PooledObject<Session> pooledObject) throws Exception {
                Session session = pooledObject.getObject();
                if (session == null || !session.isConnected()) {
                    return;
                }
                // 只清理通道，保持会话连接
                // 不注释掉会有bug    session is down
                // session.noMoreSessionChannels();
            }
        };
    }
}
