package com.zhifei.cloud.handler;

import com.zhifei.cloud.config.WebSecurityConfig;
import com.zhifei.cloud.plugin.exception.entity.R;
import com.zhifei.cloud.plugin.exception.enums.RCode;
import com.zhifei.cloud.tools.ResponseTools;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限不足处理器。使用注解的方式的权限管理不会进入这个方法，而会被全局异常管理器拦截，
 * 在{@link WebSecurityConfig}中配置的权限管理会进入此处理器
 */
@Component
public class NonAuthorityHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        ResponseTools.writeJson(response, R.fail().rCode(RCode.UNAUTHORIZED));
    }
}
