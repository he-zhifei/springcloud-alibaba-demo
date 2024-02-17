package com.zhifei.cloud.controller;

import com.zhifei.cloud.plugin.exception.annotation.GlobalException;
import com.zhifei.cloud.plugin.exception.entity.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@GlobalException
@RestController
@RequestMapping("/storage")
public class StorageController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/port")
    public R port() {
        return R.success().data("StorageController.port: " + port);
    }

    @GetMapping("/e")
    public R e() {
        throw new RuntimeException("eee");
    }
}
