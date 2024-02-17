package com.zhifei.cloud.service.impl;

import com.zhifei.cloud.dao.SysMenuMapper;
import com.zhifei.cloud.dao.SysRoleMapper;
import com.zhifei.cloud.dao.SysUserMapper;
import com.zhifei.cloud.entity.SecurityUser;
import com.zhifei.cloud.entity.SysMenu;
import com.zhifei.cloud.entity.SysRole;
import com.zhifei.cloud.entity.SysUser;
import com.zhifei.cloud.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public SysUser getByUsername(String username) {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        return sysUserMapper.selectOne(sysUser);
    }

    @Override
    public Boolean isAdmin(String username) {
        Set<Long> roleIds = sysRoleMapper.selectRoleIdsByUsername(username);
        // 这里的数据库中，管理员的权限ID为1，按实际情况判断判断
        return (roleIds != null && roleIds.contains(1L));
    }

    @Override
    public List<SysRole> getRolesByUsername(String username) {
        return sysRoleMapper.getRolesByUsername(username);
    }

    @Override
    public List<SysMenu> getMenusByUsername(String username) {
        if (isAdmin(username)) {
            // 管理员账户查询所有权限
            return sysMenuMapper.selectAll();
        }
        return sysMenuMapper.getMenusByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        if (username == null || username.trim().length() == 0) new RuntimeException("请输入用户名");
        SysUser sysUser = getByUsername(username);
        if (sysUser == null) throw new UsernameNotFoundException("用户名：" + sysUser.getUsername() + "不正确");
        sysUser.setRoles(getRolesByUsername(username));
        sysUser.setMenus(getMenusByUsername(username));
        SecurityUser securityUser = new SecurityUser(sysUser);
        return securityUser;
    }

}
