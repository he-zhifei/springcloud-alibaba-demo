package com.zhifei.cloud.controller;

import com.zhifei.cloud.feign.StorageControllerFeign;
import com.zhifei.cloud.plugin.exception.entity.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 测试服务注册发现、配置中心
 */
@RefreshScope       // 支持动态刷新
@RestController
@RequestMapping("/order")
public class OrderController {

    @Value("${services.storageUrl}")
    private String storageUrl;

//    @Value("${config.info: default-info}")
//    private String info;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StorageControllerFeign storageControllerFeign;

    /**
     * 测试使用Ribbon进行微服务通信
     * @return
     */
    @GetMapping("/testRibbon")
    public R testRibbon() {
        return restTemplate.getForObject(storageUrl + "/storage/port", R.class);
    }

    /**
     * 测试使用OpenFeign进行微服务通信
     * @return
     */
    @GetMapping("/testOpenFeign")
    public R testOpenFeign() {
        return storageControllerFeign.port();
    }

//    @GetMapping("/info")
//    public String info() {
//        return info;
//    }
}
