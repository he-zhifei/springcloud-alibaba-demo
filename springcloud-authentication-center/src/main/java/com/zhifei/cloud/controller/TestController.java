package com.zhifei.cloud.controller;

import com.zhifei.cloud.plugin.exception.annotation.GlobalException;
import com.zhifei.cloud.plugin.exception.entity.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@GlobalException
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public R test() {
        return R.success().message("test!!!!!");
    }
}
