package com.awake.ve.common.oss.constant;

import com.awake.ve.common.core.constant.GlobalConstants;

import java.util.Arrays;
import java.util.List;

/**
 * oss常量
 *
 * @author wangjiaxing
 * @date 2024/12/17 16:42
 */
public interface OssConstant {
    /**
     * 默认配置key
     */
    String DEFAULT_CONFIG_KEY = GlobalConstants.GLOBAL_REDIS_KEY + "sys_oss:default_config";

    /**
     * 预览列表资源开关key
     */
    String PREVIEW_LIST_RESOURCE_KEY = "sys.oss.previewListResource";

    /**
     * 系统数据id
     */
    List<Long> SYSTEM_DATA_IDS = Arrays.asList(1L, 2L, 3L, 4L);

    /**
     * 云服务商
     */
    String[] CLOUD_SERVICE = new String[]{"aliyun", "qcloud", "qiniu", "obs"};

    /**
     * https状态
     */
    String IS_HTTPS = "Y";
}
