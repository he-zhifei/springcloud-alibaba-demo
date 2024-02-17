package com.zhifei.cloud.handler;

import com.zhifei.cloud.constant.LoginConstants;
import com.zhifei.cloud.entity.SysUser;
import com.zhifei.cloud.plugin.exception.entity.R;
import com.zhifei.cloud.properties.JwtProperties;
import com.zhifei.cloud.tools.JwtRedisKeyGenerator;
import com.zhifei.cloud.tools.ResponseTools;
import com.zhifei.cloud.tools.jwt.JwtTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 退出登录处理器
 */
@Component
public class MyLogoutHandler implements LogoutHandler {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            // 清除redis中的缓存
            String token = request.getHeader(LoginConstants.AUTHORIZATION_NAME).replace(LoginConstants.AUTHORIZATION_PREFIX, "");
            SysUser sysUser = JwtTools.parser(jwtProperties.getRsaPublicKey()).parseJwt4Data(token, SysUser.class);
            stringRedisTemplate.delete(JwtRedisKeyGenerator.generate(sysUser.getUsername()));
        } catch (Exception e) {
        }
        ResponseTools.writeJson(response, R.success().message("已退出登录"));
    }
}
