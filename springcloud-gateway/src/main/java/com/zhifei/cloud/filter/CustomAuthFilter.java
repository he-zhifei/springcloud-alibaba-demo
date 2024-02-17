//package com.zhifei.cloud.filter;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.Date;
//
///**
// * 自定义全局过滤器
// *
// * @author He Zhifei
// * @date 2020/7/14 3:48
// */
//@Slf4j
//@Component
//public class CustomAuthFilter implements GlobalFilter, Ordered {
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//        log.info("+++++++全局认证：{}++++++++", new Date());
//
//        String username = exchange.getRequest().getQueryParams().getFirst("username");
//        if (StringUtils.isEmpty(username)) {
//            log.error("********用户名不能为空********");
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
