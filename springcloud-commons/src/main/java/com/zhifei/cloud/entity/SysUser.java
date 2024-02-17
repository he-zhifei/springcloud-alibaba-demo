package com.zhifei.cloud.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(value = {"password", "createBy", "updateBy", "pwdResetTime"}, allowSetters = true)
@Data
@Table(name = "sys_user")
public class SysUser implements Serializable {
    /**
     * 用户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * email
     */
    private String email;

    /**
     * 头像文件名
     */
    private String avatarName;

    /**
     * 头像文件地址
     */
    private String avatarPath;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 是否是管理员
     */
    private Boolean isAdmin;

    /**
     * 状态：1启用、0禁用
     */
    @Column(name = "`enabled`")
    private Long enabled;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 密码重置时间
     */
    private Date pwdResetTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @Transient
    private List<SysRole> roles;

    @Transient
    private List<SysMenu> menus;

    @Transient
    private String token;

}
