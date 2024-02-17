//package com.zhifei.springcloud.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Gateway: 路由、断言、过滤器
// *
// * @author He Zhifei
// * @date 2020/7/14 1:22
// */
//@Configuration
//public class GatewayConfig {
//
//    /**
//     * 这里的配置和yml的路由配置起到同样的效果：
//     * localhost:9600/order/**的请求被转发到localhost:80/
//     * localhost:9600/payment/**的请求被转发到localhost:8001，其中uri值的后面那一串不会造成任何影响，比如这里的/abc/**
//     * @param builder
//     * @return
//     */
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        RouteLocatorBuilder.Builder routes = builder.routes();
//
////        routes.route("consumer_order_route_id", r -> r.path("/order/**").uri("http://localhost:80")).build();
////
////        routes.route("provider_payment_route_id", r -> r.path("/payment/**").uri("http://localhost:8001/abc/**")).build();
//
//        routes.route("consumer_order_route_id", r -> r.path("/order/**").uri("lb://springcloud-order-serivce")).build();
//
//        routes.route("provider_payment_route_id", r -> r.path("/payment/**").uri("lb://springcloud-payment-service")).build();      //springcloud-payment-service集群下有两个实例
//
//        return routes.build();
//    }
//}
