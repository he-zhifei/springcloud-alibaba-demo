package com.zhifei.cloud.tools;

import com.zhifei.cloud.constant.LoginConstants;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class JwtRedisKeyGenerator {

    /**
     * 生成规则：前缀+md5(username)+"_"+md5(user-agent)
     *
     * @param username
     * @return
     */
    public static final String generate(String username) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return new StringBuilder(LoginConstants.REDIS_JWT_PREFIX)
                .append(SecurityTools.md5(username)).append("_")
                .append(SecurityTools.md5(request.getHeader("User-Agent"))).toString();
    }
}
