package com.awake.ve.common.ecs.constants;


/**
 * @author wangjiaxing
 * @date 2025/2/22 13:06
 */
public interface ApiParamConstants {
    /**
     * 节点
     */
    String NODE = "node";
    /**
     * 服务器主机
     */
    String HOST = "host";
    /**
     * 服务端口
     */
    String PORT = "port";
    /**
     * api用户名
     */
    String API_USERNAME = "username";
    /**
     * api用户密码
     */
    String API_PASSWORD = "password";
    /**
     * 虚拟机id
     */
    String VM_ID = "vmid";
    /**
     * pve token请求头
     */
    String CSRF_PREVENTION_TOKEN = "CSRFPreventionToken";
    /**
     * Cookie
     */
    String COOKIE = "Cookie";
    /**
     * pve cookie
     */
    String PVE_AUTH_COOKIE = "PVEAuthCookie=";
    /**
     * 新的id
     */
    String NEW_ID = "newid";
    /**
     * 名称
     */
    String NAME = "name";
    /**
     * content-type
     */
    String CONTENT_TYPE = "Content-Type";
    /**
     * json请求类型
     */
    String APPLICATION_JSON = "application/json";
    /**
     * IO带宽限制
     */
    String BW_LIMIT = "bwlimit";
    /**
     * 描述
     */
    String DESCRIPTION = "description";
    /**
     * 格式
     */
    String FORMAT = "format";
    /**
     * 是否完整克隆
     */
    String FULL = "full";
    /**
     * 存储池
     */
    String POOL = "pool";
    /**
     * 快照名称
     */
    String SNAPNAME = "snapname";
    /**
     * 存储
     */
    String STORAGE = "storage";
    /**
     * 目标
     */
    String TARGET = "target";
    /**
     * 强制覆盖cpu
     */
    String FORCE_CPU = "force_cpu";
    /**
     * 虚拟机类型
     */
    String MACHINE = "machine";
    /**
     * 原节点
     */
    String MIGRATED_FROM = "migratedfrom";
    /**
     * 迁移使用的网络的CIDR
     */
    String MIGRATION_NETWORK = "migration_network";
    /**
     * 迁移类型
     */
    String MIGRATION_TYPE = "migration_type";
    /**
     * 忽略锁定
     */
    String SKIP_LOCK = "skiplock";
    /**
     * 状态uri
     */
    String STATE_URI = "stateuri";
    /**
     * 目标存储
     */
    String TARGET_STORAGE = "targetstorage";
    /**
     * 超时
     */
    String TIMEOUT = "timeout";
    /**
     * 强制停止
     */
    String FORCE_STOP = "forceStop";
    /**
     * 保持连接
     */
    String KEEP_ALIVE = "keepAlive";
    /**
     * 覆盖之前的未完成的shutdown
     */
    String OVERRULE_SHUTDOWN = "overruleShutdown";
}
