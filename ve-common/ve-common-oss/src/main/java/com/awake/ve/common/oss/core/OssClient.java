package com.awake.ve.common.oss.core;

import com.awake.ve.common.core.constant.Constants;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.oss.constant.OssConstant;
import com.awake.ve.common.oss.exception.OssException;
import com.awake.ve.common.oss.properties.OssProperties;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.crt.S3CrtHttpConfiguration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

import java.net.URI;
import java.time.Duration;

/**
 * S3 存储协议 所有兼容S3协议的云厂商均支持
 * 阿里云 腾讯云 七牛云 minio
 *
 * @author wangjiaxing
 * @date 2024/12/17 17:30
 */
public class OssClient {
    /**
     * 服务商
     */
    private final String configKey;

    /**
     * 配置属性
     */
    private final OssProperties properties;

    /**
     * Amazon S3 异步客户端
     */
    private final S3AsyncClient client;

    /**
     * 用于管理S3数据传输的高级工具
     */
    private final S3TransferManager transferManager;

    /**
     * AWS S3 预签名URL的生成器
     */
    private final S3Presigner presigner;

    public OssClient(String configKey, OssProperties ossProperties) {
        this.configKey = configKey;
        this.properties = ossProperties;

        try {
            // AWS 认证信息
            StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey())
            );

            // MinIO使用Https限制使用域名访问,站点填域名,需要启用路径样式访问
            boolean isStyle = !StringUtils.containsAny(properties.getEndpoint(), OssConstant.CLOUD_SERVICE);

            // 创建AWS基于CRT的S3客户端
            this.client = S3AsyncClient.crtBuilder()
                    .credentialsProvider(credentialsProvider)
                    .endpointOverride(URI.create(getEndpoint()))
                    .region(of())
                    .targetThroughputInGbps(20.0)
                    .minimumPartSizeInBytes(10 * 1025 * 1024L)
                    .checksumValidationEnabled(false)
                    .forcePathStyle(isStyle)
                    .httpConfiguration(S3CrtHttpConfiguration.builder()
                            .connectionTimeout(Duration.ofSeconds(60)) // 连接超时
                            .build()
                    )
                    .build();

            // AWS基于CRT的S3 AsyncClient示例用作S3传输管理器的底层客户端
            this.transferManager = S3TransferManager.builder().s3Client(this.client).build();

            // 创建S3配置对象
            S3Configuration config = S3Configuration.builder()
                    .checksumValidationEnabled(false)
                    .pathStyleAccessEnabled(isStyle)
                    .build();

            // 创建AWS S3 预签名URL的生成器
            this.presigner = S3Presigner.builder()
                    .region(of())
                    .credentialsProvider(credentialsProvider)
                    .endpointOverride(URI.create(getEndpoint()))
                    .serviceConfiguration(config)
                    .build();
        } catch (Exception e) {
            if (e instanceof OssException) {
                throw e;
            }
            throw new OssException("配置错误! 请检查系统配置:[" + e.getMessage() + "]");
        }
    }

    /**
     * 根据传入的region参数 返回相应的AWS区域
     * 如果region非空,使用Region.of(),创建并返回对应的AWS区域对象
     * 如果region为空,则返回一个默认的,作为广泛支持的区域
     *
     * @author wangjiaxing
     * @date 2024/12/17 18:21
     */
    public Region of() {
        // AWS 区域字符串
        String region = properties.getRegion();
        // 返回AWS区域对象
        return StringUtils.isNotEmpty(region) ? Region.of(region) : Region.US_EAST_1;
    }

    /**
     * 获取S3客户端的终端点 URL
     *
     * @author wangjiaxing
     * @date 2024/12/17 18:24
     */
    public String getEndpoint() {
        // 根据配置文件中,是否使用Https,设置协议头部
        String header = getIsHttps();
        // 拼接协议头部和终端点,得到完整的终端点 URL
        return header + properties.getEndpoint();
    }

    /**
     * 获取是否使用HTTPS的配置,并返回相应的头部协议
     *
     * @author wangjiaxing
     * @date 2024/12/17 18:25
     */
    public String getIsHttps() {
        return OssConstant.IS_HTTPS.equals(properties.getIsHttps()) ? Constants.HTTPS : Constants.HTTP;
    }

    /**
     * 检查配置是否相同
     */
    public boolean checkPropertiesSame(OssProperties properties) {
        return this.properties.equals(properties);
    }
}
