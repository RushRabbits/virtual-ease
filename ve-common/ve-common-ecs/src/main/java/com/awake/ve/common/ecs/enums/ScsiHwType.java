package com.awake.ve.common.ecs.enums;

import lombok.Getter;

@Getter
public enum ScsiHwType {
    LSI("lsi","lsi"),
    LSI_53C810("lsi53c810","lsi53c810"),
    VIRTIO_SCSI_PCI("virtio-scsi-pci","virtio-scsi-pci"),
    VIRTIO_SCSI_SINGLE("virtio-scsi-single","virtio-scsi-single"),
    MEGA_SAS("megasas","megasas"),
    PVS_CSI("pvscsi","pvscsi")
    ;

    private final String type;
    private final String description;

    ScsiHwType(String type, String description) {
        this.type = type;
        this.description = description;
    }
}
