package com.zhifei.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author He Zhifei
 * @date 2020/7/17 23:14
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)     // 动态数据源需要忽略自动配置
@EnableDiscoveryClient
@EnableFeignClients
public class AuthenticationCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticationCenterApplication.class, args);
    }
}
