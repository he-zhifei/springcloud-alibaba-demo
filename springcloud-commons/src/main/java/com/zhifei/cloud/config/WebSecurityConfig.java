package com.zhifei.cloud.config;

import com.zhifei.cloud.filter.JwtVerifyFilter;
import com.zhifei.cloud.handler.NonAuthorityHandler;
import com.zhifei.cloud.handler.UnAuthHandler;
import com.zhifei.cloud.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @EnableGlobalMethodSecurity 开启方法注解，其中在方法或者类上可以使用三套注解，用哪一套都可以：
 * 1.Spring Security's {@link PreAuthorize}, {@link PreFilter}, {@link PostAuthorize}, {@link PostFilter} annotations：prePostEnabled = true，例子：@PreAuthorize("ROLE_NAME")
 * 使用这套注解时，需要用到EL表达式：例如：@PreAuthorize("hasAnyRole('PRODUCT')")     @PreAuthorize("hasAnyAuthority('ROLE_PRODUCT')")
 * has...Role和has...Authorize里面参数的区别：前者会自动拼接ROLE_，建议使用后者，避免混淆，因为原本的角色名称为ROLE_PRODUCT
 *
 * 2.Spring Security's {@link Secured} annotations：securedEnabled = true，例子：@Secured("ROLE_NAME")
 *
 * 3.JSR-250 annotations：jsr250Enabled = true，例子：@RolesAllowed("ROLE_NAME")
 *
 * @author He Zhifei
 * @date 2020/7/1 15:39
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UnAuthHandler unAuthHandler;

    @Autowired
    private NonAuthorityHandler nonAuthorityHandler;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 自定义配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilter(new JwtVerifyFilter(super.authenticationManager(), jwtProperties.getRsaPublicKey(), stringRedisTemplate))
                .authorizeRequests()
                .anyRequest().authenticated()
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(unAuthHandler)
                .accessDeniedHandler(nonAuthorityHandler)
            .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
