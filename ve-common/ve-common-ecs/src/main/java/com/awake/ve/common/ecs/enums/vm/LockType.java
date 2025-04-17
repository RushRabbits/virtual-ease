package com.awake.ve.common.ecs.enums.vm;

import lombok.Getter;

/**
 * lock枚举
 *
 * @author wangjiaxing
 * @date 2025/2/25 12:14
 */
@Getter
public enum LockType {
    BACKUP("backup", "备份中"),
    CLONE("clone", "克隆中"),
    CREATE("create", "创建中"),
    MIGRATE("migrate", "迁移中"),
    ROLLBACK("rollback", "回滚中"),
    SNAPSHOT("snapshot", "快照创建中"),
    SNAPSHOT_DELETE("snapshot-delete", "快照删除中"),
    SUSPENDING("suspending", "挂起过程中"),
    SUSPENDED("suspended", "已挂起");

    private final String type;
    private final String description;

    LockType(String type, String description) {
        this.type = type;
        this.description = description;
    }
}