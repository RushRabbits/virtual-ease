package com.awake.ve.common.rateLimiter.enums;

/**
 * 限流的类型
 *
 * @author wangjiaxing
 * @date 2025/1/8 10:05
 */
public enum LimitType {

    /**
     * 默认策略 全局限流
     */
    DEFAULT,

    /**
     * IP限流
     */
    IP,

    /**
     * 实例限流
     */
    CLUSTER
}
