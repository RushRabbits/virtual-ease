package com.awake.ve.common.core.constant;

/**
 * 缓存的key 常量
 *
 * @author Lion Li
 */
public interface CacheConstants {

    /**
     * 在线用户 translation key
     */
    String ONLINE_TOKEN_KEY = "online_tokens:";

    /**
     * 参数管理 cache key
     */
    String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    String SYS_DICT_KEY = "sys_dict:";

    /**
     * 登录账户密码错误次数 translation key
     */
    String PWD_ERR_CNT_KEY = "pwd_err_cnt:";

    /**
     * 分片文件的原文件名
     */
    String FRAGMENT_FILE_NAME = "fragment_file_name:";
}
