package com.zhifei.cloud.filter;

import com.zhifei.cloud.entity.SysUser;
import com.zhifei.cloud.plugin.exception.entity.R;
import com.zhifei.cloud.tools.JacksonTools;
import com.zhifei.cloud.tools.ResponseTools;
import com.zhifei.cloud.tools.ValidatorTools;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 账号密码认证
 *
 * @author He Zhifei
 * @date 2020/7/18 0:21
 */
public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private String rsaPrivateKey;

    public JwtAuthFilter(AuthenticationManager authenticationManager, String rsaPrivateKey) {
        this.authenticationManager = authenticationManager;
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public JwtAuthFilter(AuthenticationManager authenticationManager, String rsaPrivateKey, String loginUrl) {
        this(authenticationManager, rsaPrivateKey);
        // 指定登录url，默认 /login
        if (loginUrl != null && loginUrl.trim().length() != 0) {
            super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(loginUrl));
        }
    }

    /**
     * 1.校验请求方法必须为POST
     * 2.数据校验
     * 3.调用AuthenticationManager进行认证
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        // 请求方法校验
        if (!request.getMethod().equals("POST")) throw new RuntimeException("认证接口只允许POST请求");

        // 获取认证请求数据
        SysUser sysUser = null;
        try {
            sysUser = JacksonTools.fromInputStream(request.getInputStream(), SysUser.class);
        } catch (IOException e) {
            throw new RuntimeException("认证数据请放在请求体中");
        }
        if (sysUser == null) throw new RuntimeException("Content-Type必须为application/json");

        // 校验认证信息格式
        Map validateResult = ValidatorTools.validateModel(sysUser);
        if (validateResult != null) {
            ResponseTools.writeJson(response, R.fail().data(validateResult));
            return null;
        }

//        SysUser sysUser = new SysUser(); sysUser.setUsername("测试用户"); sysUser.setPassword("123456");

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                sysUser.getUsername(), sysUser.getPassword());
        return authenticationManager.authenticate(authRequest);
    }

}
