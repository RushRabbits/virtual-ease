package com.awake.ve.common.ecs.enums;

import lombok.Getter;

@Getter
public enum QemuCommand {
    /**
     * image格式镜像转为qcow2格式镜像
     */
    IMG_TO_QCOW2(
            "qemu-img convert -p -O qcow2 {originImagePath} {newImageNameWithSuffix}",
            "IMG_TO_QCOW2",
            "将img格式的镜像转为qcow2格式,originImagePath为为原镜像绝对路径,newImageNameWithSuffix为新镜像全名"
    ),
    /**
     * 为虚拟机导入磁盘镜像
     */
    IMPORT_DISKIMAGE_TO_VM(
            "qm importdisk {vmId} {targetDiskImagePath} {storagePool} --format={vmFormat}",
            "IMPORT_DISKIMAGE_TO_VM",
            "为虚拟机导入磁盘,vmId为虚拟机id,targetDiskImagePath为目标磁盘镜像的绝对路径," +
                    "storagePool为资源存储池,调试阶段只保留了local,可默认为local,vmFormat为期待的虚拟机格式,本系统推荐使用qcow2"
    ),
    /**
     * 启动虚拟机
     */
    VM_START("qm start {vmId}",
            "VM_START",
            "启动虚拟机"),
    /**
     * 停止虚拟机
     */
    VM_STOP("qm stop {vmId}",
            "VM_STOP",
            "停止虚拟机"
    ),
    /**
     * 强制停止虚拟机
     */
    VM_MUST_STOP("rm /var/lock/qemu-server/lock-{vmId}.conf && qm stop {vmId}",
            "VM_MUST_STOP",
            "强制关闭虚拟机. 在调试中发现,有时虚拟机无法关闭是由于该配置文件被一个线程一直占用且加了锁,导致关闭虚拟机的指令卡死在这里"),
    /**
     * 停止虚拟机
     */
    VM_SHUTDOWN("qm shutdown {vmId}",
            "VM_SHUTDOWN",
            "停止虚拟机"
    ),
    /**
     * 强制停止虚拟机
     */
    VM_MUST_SHUTDOWN("rm /var/lock/qemu-server/lock-{vmId}.conf && qm shutdown {vmId}",
            "VM_MUST_SHUTDOWN",
            "强制关闭虚拟机. 在调试中发现,有时虚拟机无法关闭是由于该配置文件被一个线程一直占用且加了锁,导致关闭虚拟机的指令卡死在这里"),
    /**
     * 虚拟机转为模板
     */
    VM_TO_TEMPLATE("qm template {vmId}",
            "VM_TO_TEMPLATE",
            "将虚拟机转化为模板,注意此虚拟机一定要先关闭,不然会失败,建议先执行关闭命令"),
    /**
     * 根据模板创建虚拟机
     */
    TEMPLATE_CREATE_VM("qm clone {templateId} {newVmId} --name={newVmName}",
            "TEMPLATE_CREATE_VM",
            "根据模板创建虚拟机,templateId为模板id,newVmId为新虚拟机id,newVmName为新虚拟机名"),
    /**
     * 查询虚拟机列表
     */
    VM_LIST("qm list",
            "VM_LIST",
            "查询虚拟机列表,会展示出系统所有的虚拟机以及模板的信息"),
    /**
     * 虚拟机/模板详细信息
     */
    VM_AND_TEMPLATE_DETAIL("qm config {vmId}",
            "VM_AND_TEMPLATE_DETAIL",
            "虚拟机/模板详细信息,vmId为虚拟机/模板id"),
    /**
     * 获取虚拟机的spice代理
     */
    VM_SPICE_PROXY("pvesh create /nodes/{node}/qemu/{vmId}/spiceproxy",
            "VM_SPICE_PROXY",
            "获取虚拟机的spice代理");

    /**
     * pve的命令模板
     */
    private final String command;
    /**
     * 命令名称
     */
    private final String name;
    /**
     * 命令描述
     */
    private final String description;

    QemuCommand(String command, String name, String description) {
        this.command = command;
        this.name = name;
        this.description = description;
    }

    public static QemuCommand getByName(String name) {
        for (QemuCommand qemuCommand : QemuCommand.values()) {
            if (qemuCommand.getName().equals(name)) {
                return qemuCommand;
            }
        }
        return null;
    }

}
