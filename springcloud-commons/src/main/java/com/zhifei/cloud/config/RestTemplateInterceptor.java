package com.zhifei.cloud.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 解决{@link RestTemplate}进行服务间调用时，请求头丢失，导致校验失败的问题。在实例化RestTemplate后，
 * 设置当前拦截器{@link org.springframework.http.client.support.InterceptingHttpAccessor#setInterceptors(java.util.List)}
 *
 * @author He Zhifei
 * @date 2020/8/26 10:27
 */
@Component
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        // 添加HttpServletRequest中请求头信息
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Enumeration<String> headerNames = req.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                headers.add(headerName, req.getHeader(headerName));
            }
        }
        // 保证请求继续被执行
        return execution.execute(request, body);
    }
}
