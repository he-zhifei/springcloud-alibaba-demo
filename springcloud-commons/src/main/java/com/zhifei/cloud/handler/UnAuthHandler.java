package com.zhifei.cloud.handler;

import com.zhifei.cloud.plugin.exception.entity.R;
import com.zhifei.cloud.plugin.exception.enums.RCode;
import com.zhifei.cloud.tools.ResponseTools;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未登录处理器
 */
@Component
public class UnAuthHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ResponseTools.writeJson(response, R.fail().rCode(RCode.NEED_RELOGIN));
    }
}
