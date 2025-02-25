package com.awake.ve.common.ecs.enums;

import lombok.Getter;

/**
 * keyboard枚举
 *
 * @author wangjiaxing
 * @date 2025/2/25 12:14
 */
@Getter
public enum KeyBoard {
    DE("de", "德国"),
    DE_CH("de-ch", "瑞士德语"),
    DA("da", "丹麦"),
    EN_GB("en-gb", "英国英语"),
    EN_US("en-us", "美国英语"),
    ES("es", "西班牙"),
    FI("fi", "芬兰"),
    FR("fr", "法国"),
    FR_BE("fr-be", "比利时法语"),
    FR_CA("fr-ca", "加拿大法语"),
    FR_CH("fr-ch", "瑞士法语"),
    HU("hu", "匈牙利"),
    IS("is", "冰岛"),
    IT("it", "意大利"),
    JA("ja", "日本"),
    LT("lt", "立陶宛"),
    MK("mk", "马其顿"),
    NL("nl", "荷兰"),
    NO("no", "挪威"),
    PL("pl", "波兰"),
    PT("pt", "葡萄牙"),
    PT_BR("pt-br", "巴西葡萄牙语"),
    SV("sv", "瑞典"),
    SL("sl", "斯洛文尼亚"),
    TR("tr", "土耳其");

    private final String type;
    private final String description;

    KeyBoard(String type, String description) {
        this.type = type;
        this.description = description;
    }
}