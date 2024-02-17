package com.zhifei.cloud.dao;

import com.zhifei.cloud.entity.SysMenu;
import com.zhifei.cloud.plugin.tkmybatis.BaseMapper;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<SysMenu> getMenusByUsername(String username);
}

