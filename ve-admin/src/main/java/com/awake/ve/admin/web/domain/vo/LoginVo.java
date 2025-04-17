package com.awake.ve.admin.web.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 登录验证信息
 *
 * @author wangjiaxing
 * @date 2025/2/12 9:17
 */
@Data
public class LoginVo {

    /**
     * 授权令牌
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * 刷新令牌
     */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * 授权令牌的有效时间
     */
    @JsonProperty("expire_in")
    private Long expireIn;

    /**
     * 刷新令牌的有效时间
     */
    @JsonProperty("refresh_expire_in")
    private Long refreshExpireIn;

    /**
     * 客户端id
     */
    @JsonProperty("client_id")
    private String clientId;

    /**
     * 令牌权限
     */
    private String scope;

    /**
     * 用户openId
     */
    private String openid;
}
