package com.awake.ve.common.ecs.domain.network.children;

import com.awake.ve.common.ecs.domain.network.Network;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class VlanNetwork extends Network {

    /**
     * VLAN所在的物理网卡
     */
    private String vlanRawDevice;
}
