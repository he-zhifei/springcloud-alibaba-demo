package com.zhifei.cloud.blockHandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * 自定义的统一处理熔断降级的方法，基本规则与
 * com.zhifei.springcloud.controller.SentinelController#bh(java.lang.String, com.alibaba.csp.sentinel.slots.block.BlockException)
 * 一致，但必须为静态方法
 */
public class CustomBlockHandler {

    public static String blockHandler(String a, BlockException e) {
//        System.out.println(e.getMessage());
        return "CustomBlockHandler.blockHandler...";
    }

}
