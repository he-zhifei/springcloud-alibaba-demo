package com.zhifei.cloud.dao;

import com.zhifei.cloud.entity.SysRole;
import com.zhifei.cloud.plugin.tkmybatis.BaseMapper;

import java.util.List;
import java.util.Set;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<SysRole> getRolesByUsername(String username);
    Set<Long> selectRoleIdsByUsername(String username);
}
