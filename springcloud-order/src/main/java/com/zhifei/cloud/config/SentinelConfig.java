package com.zhifei.cloud.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class SentinelConfig {

    @PostConstruct
    public void init() {
        // 授权规则--黑白名单，这里通过请求ip进行限制
        WebCallbackManager.setRequestOriginParser(req -> req.getRemoteAddr());
    }
}
