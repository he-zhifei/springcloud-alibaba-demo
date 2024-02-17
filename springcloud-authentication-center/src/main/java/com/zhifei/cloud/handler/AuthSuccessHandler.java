package com.zhifei.cloud.handler;

import com.zhifei.cloud.constant.LoginConstants;
import com.zhifei.cloud.entity.SecurityUser;
import com.zhifei.cloud.entity.SysUser;
import com.zhifei.cloud.plugin.exception.entity.R;
import com.zhifei.cloud.properties.JwtProperties;
import com.zhifei.cloud.tools.JacksonTools;
import com.zhifei.cloud.tools.JwtRedisKeyGenerator;
import com.zhifei.cloud.tools.ResponseTools;
import com.zhifei.cloud.tools.jwt.JwtTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 登录成功处理器
 */
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 1.通过用户信息-rsa颁发证书（token）
     * 2.把token放到请求头
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
            SysUser sysUser = securityUser.getSysUser();
            // 密码设置为null
            sysUser.setPassword(null);
            // 这里只通过用户ID和用户名生成token，并存放到redis，value为认证后的权限（准确来说是用户的角色与权限的集合）
            SysUser userInfo = new SysUser();
            userInfo.setUserId(sysUser.getUserId());
            userInfo.setUsername(sysUser.getUsername());
            String token = JwtTools.builder(jwtProperties.getRsaPrivateKey()).generateJwt(userInfo, jwtProperties.getTokenValiditySeconds());
            sysUser.setToken(LoginConstants.AUTHORIZATION_PREFIX + token);

            stringRedisTemplate.opsForValue().set(JwtRedisKeyGenerator.generate(sysUser.getUsername()), JacksonTools.toJson(securityUser.getAuthorities()),
                    jwtProperties.getTokenValiditySeconds(), TimeUnit.SECONDS);
            ResponseTools.writeJson(response, R.success().message("认证成功").data(sysUser));
        } catch (Exception e) {
            e.printStackTrace();
            ResponseTools.writeJson(response, R.fail().message("证书颁发异常"));
            return;
        }
    }
}
