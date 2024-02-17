package com.zhifei.cloud.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class SecurityUser implements UserDetails {

    private SysUser sysUser;

    public SecurityUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SysRole> roles = sysUser.getRoles();
        List<SysMenu> menus = sysUser.getMenus();
        int len = 0;
        if (roles != null) len += roles.size();
        if (menus != null) len += menus.size();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>(len);
        Optional.ofNullable(roles).orElse(Collections.emptyList()).stream().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        Optional.ofNullable(menus).orElse(Collections.emptyList()).stream()
                .filter(menu -> menu.getPermission() != null && menu.getPermission().trim().length() != 0)      // 过滤掉权限为空的数据
                .forEach(menu -> authorities.add(new SimpleGrantedAuthority(menu.getPermission())));
        return authorities;
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return sysUser.getEnabled() == 1;
    }
}
