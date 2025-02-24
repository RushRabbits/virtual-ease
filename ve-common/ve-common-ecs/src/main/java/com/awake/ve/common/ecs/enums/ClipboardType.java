package com.awake.ve.common.ecs.enums;

import lombok.Getter;

@Getter
public enum ClipboardType {
    VNC("vnc", "vnc"),
    SPICE("spice", "spice"),
    ;

    private final String type;
    private final String description;

    ClipboardType(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public static ClipboardType getClipboardType(String type) {
        for (ClipboardType clipboardType : ClipboardType.values()) {
            if (clipboardType.getType().equals(type)) {
                return clipboardType;
            }
        }
        return null;
    }
}
