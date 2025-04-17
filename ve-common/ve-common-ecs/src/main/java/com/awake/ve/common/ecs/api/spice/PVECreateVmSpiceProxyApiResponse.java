package com.awake.ve.common.ecs.api.spice;

import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * pve api 创建spice代理连接 响应
 *
 * @author wangjiaxing
 * @date 2025/2/27 14:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PVECreateVmSpiceProxyApiResponse implements BaseApiResponse {

    /**
     * 主机地址
     */
    private String host;

    /**
     * 标题
     */
    private String title;

    /**
     * 主机证书的主题信息
     */
    private String hostSubject;

    /**
     * 切换全屏模式的快捷键(Shift + F11)
     */
    private String toggleFullScreen;

    /**
     * CA证书
     */
    private String ca;

    /**
     * 密码
     */
    private String password;

    /**
     * 代理服务器
     */
    private String proxy;

    /**
     * tls端口
     */
    private Integer tlsPort;

    /**
     * 连接类型
     */
    private String type;

    /**
     * 发送安全注意键组合的快捷键(类似于Windows的Ctrl+Alt+Del)
     */
    private String secureAttention;

    /**
     * 删除此文件
     * 0: 否
     * 1: 是
     */
    private Integer deleteThisFile;

    /**
     * 释放鼠标光标的快捷键，用于在SPICE客户端和本地系统之间切换鼠标控制(Ctrl+Alt+R)
     */
    private String releaseCursor;
}
