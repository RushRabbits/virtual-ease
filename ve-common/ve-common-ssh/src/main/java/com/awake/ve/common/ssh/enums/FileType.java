package com.awake.ve.common.ssh.enums;

import lombok.Getter;

/**
 * linux系统文件类型枚举
 *
 * @author wangjiaxing
 * @date 2025/2/19 14:51
 */
@Getter
public enum FileType {
    REGULAR_FILE('-'),
    DIRECTORY('d'),
    SYMBOLIC_LINK('l'),
    CHARACTER_DEVICE('c'),
    BLOCK_DEVICE('b'),
    SOCKET('s'),
    FIFO('p');

    private final char symbol;

    FileType(char symbol) {
        this.symbol = symbol;
    }

    public static FileType fromChar(char c) {
        for (FileType type : values()) {
            if (type.symbol == c) {
                return type;
            }
        }
        return REGULAR_FILE; // 默认为普通文件
    }

    public char getSymbol() {
        return symbol;
    }
}
