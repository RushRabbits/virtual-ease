package com.awake.ve.system.mapper;

import com.awake.ve.common.mybatis.core.mapper.BaseMapperPlus;
import com.awake.ve.system.domain.SysUserRole;

import java.util.List;

/**
 * 用户与角色关联表 数据层
 *
 * @author Lion Li
 */
public interface SysUserRoleMapper extends BaseMapperPlus<SysUserRole, SysUserRole> {

    List<Long> selectUserIdsByRoleId(Long roleId);

}
