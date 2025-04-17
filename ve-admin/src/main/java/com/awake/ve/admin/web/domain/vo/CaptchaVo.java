package com.awake.ve.admin.web.domain.vo;

import lombok.Data;

/**
 * 验证码VO
 *
 * @author wangjiaxing
 * @date 2025/2/12 9:13
 */
@Data
public class CaptchaVo {

    /**
     * 是否开启验证码
     */
    private Boolean captchaEnabled = true;

    private String uuid;

    /**
     * 验证码图片
     */
    private String img;
}
