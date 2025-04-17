package com.awake.ve.common.ssh.enums;

import lombok.Getter;

/**
 * Pseudo Terminal（伪终端）的缩写
 * 伪终端类型枚举
 *
 * @author wangjiaxing
 * @date 2025/2/20 15:02
 */
@Getter
public enum PtyType {
    VT102("vt102",
            "DEC 公司开发的经典终端标准\n" +
                    "提供基本的终端功能：\n" +
                    "\n" +
                    "光标移动和定位\n" +
                    "简单的文本格式化\n" +
                    "基本的屏幕清除操作\n" +
                    "\n" +
                    "\n" +
                    "广泛兼容，是很多系统的默认终端类型\n" +
                    "功能相对简单，但稳定可靠"),
    XTERM("xterm",
            "X Window System 的标准终端模拟器\n" +
                    "提供更丰富的功能：\n" +
                    "\n" +
                    "完整的颜色支持（256色或真彩色）\n" +
                    "鼠标事件处理\n" +
                    "窗口操作\n" +
                    "Unicode 字符支持\n" +
                    "扩展的键盘支持\n" +
                    "\n" +
                    "\n" +
                    "现代终端模拟器的标准选择\n" +
                    "向后兼容 VT102"),
    ANSI("ansi",
            "美国国家标准协会(ANSI)定义的终端标准\n" +
                    "支持：\n" +
                    "\n" +
                    "基本的文本颜色和格式\n" +
                    "光标控制\n" +
                    "屏幕清除\n" +
                    "\n" +
                    "\n" +
                    "在 DOS/Windows 环境中常用\n" +
                    "功能介于 VT102 和 xterm 之间\n" +
                    "\n" +
                    "\n"),
    DUMB("dumb",
            "最基本的终端类型\n" +
                    "几乎没有特殊功能：\n" +
                    "\n" +
                    "不支持颜色\n" +
                    "不支持光标控制\n" +
                    "不支持屏幕清除\n" +
                    "\n" +
                    "\n" +
                    "主要用于最基本的文本输入输出\n" +
                    "在资源受限或只需要纯文本处理时使用");

    private final String type;
    private final String description;

    PtyType(String type, String description) {
        this.type = type;
        this.description = description;
    }
}
