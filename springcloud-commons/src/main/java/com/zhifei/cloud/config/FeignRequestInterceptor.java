package com.zhifei.cloud.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 解决OpenFeign进行服务间调用时，请求头丢失，导致校验失败的问题
 * 哪个微服务使用了OpenFeign进行远程服务调用，则在哪个微服务的IOC容器注入这个拦截器
 *
 * @author He Zhifei
 * @date 2020/8/26 11:25
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 添加HttpServletRequest中请求头信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                requestTemplate.header(headerName, request.getHeader(headerName));
            }
        }
    }
}
