package com.zhifei.cloud.handler;

import com.zhifei.cloud.plugin.exception.entity.R;
import com.zhifei.cloud.plugin.exception.enums.RCode;
import com.zhifei.cloud.tools.ResponseTools;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理器
 */
@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        R r = null;
        if (e instanceof UsernameNotFoundException) {
            r = R.fail().message(e.getMessage());
        } else if (e instanceof BadCredentialsException) {
            r = R.fail().rCode(RCode.INCORRECT_CREDENTIALS);
        } else {
            r = R.fail().message("用户名或密码输入有误");
        }
        ResponseTools.writeJson(response, r);
    }
}
