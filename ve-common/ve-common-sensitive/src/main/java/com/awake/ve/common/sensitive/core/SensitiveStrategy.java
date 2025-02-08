package com.awake.ve.common.sensitive.core;

import cn.hutool.core.util.DesensitizedUtil;
import lombok.AllArgsConstructor;

import java.util.function.Function;

/**
 * 脱敏策略的枚举
 *
 * @author wangjiaxing
 * @date 2025/2/8 14:39
 */
@AllArgsConstructor
public enum SensitiveStrategy {

    /**
     * 身份证脱敏
     */
    ID_CARD(idCard -> DesensitizedUtil.idCardNum(idCard, 1, 4)),

    /**
     * 移动电话脱敏
     */
    MOBILE_PHONE(DesensitizedUtil::mobilePhone),

    /**
     * 固定电话脱敏
     */
    FIXED_PHONE(DesensitizedUtil::fixedPhone),

    /**
     * 地址脱敏
     */
    ADDRESS(address -> DesensitizedUtil.address(address, 8)),

    /**
     * 邮箱脱敏
     */
    EMAIL(DesensitizedUtil::email),

    /**
     * 银行卡脱敏
     */
    BANK_CARD(DesensitizedUtil::bankCard),

    /**
     * 中文名脱敏
     */
    CHINESE_NAME(DesensitizedUtil::chineseName),

    /**
     * 用户id脱敏
     */
    USER_ID(userId -> String.valueOf(DesensitizedUtil.userId())),

    /**
     * 密码脱敏
     */
    PASSWORD(DesensitizedUtil::password),

    /**
     * ipv4脱敏
     */
    IPV4(DesensitizedUtil::ipv4),

    /**
     * ipv6脱敏
     */
    IPV6(DesensitizedUtil::ipv6),

    /**
     * 中国大陆车牌,包括新能源及油车
     */
    CAR_LICENSE(DesensitizedUtil::carLicense),

    /**
     * 只展示第一个字符
     */
    FIRST_MASK(DesensitizedUtil::firstMask),

    /**
     * 清空为null
     */
    CLEAR_TO_NULL(s -> DesensitizedUtil.clearToNull()),

    /**
     * 清空为空字符串
     */
    CLEAR_TO_EMPTY(s -> DesensitizedUtil.clear()),
    ;

    // 更多脱敏策略以及实现可自行添加

    private final Function<String, String> desensitizer;

    public Function<String, String> desensitizer() {
        return desensitizer;
    }
}