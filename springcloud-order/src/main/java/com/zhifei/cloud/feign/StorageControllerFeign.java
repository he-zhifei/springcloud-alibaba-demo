package com.zhifei.cloud.feign;

import com.zhifei.cloud.plugin.exception.entity.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "springcloud-storage")
@RequestMapping("/storage")
public interface StorageControllerFeign {

    @GetMapping("/port")
    public R port();
}
