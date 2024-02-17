package com.zhifei.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.zhifei.cloud.blockHandler.CustomBlockHandler;
import com.zhifei.cloud.fallbackClass.CustomFallbackClass;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试流量控制、熔断、降级
 *
 * 说明：fallback优先级高于defaultFallback，而配置了fallbackClass会在指定的类找对应的fallback，
 * 尽管当前类已经存在了这样的fallback
 */
@RestController
@RequestMapping("/sentinel")
public class SentinelController {

//    @SentinelResource(value = "test1", blockHandler = "bh")
    @SentinelResource(
            value = "test1",
            blockHandlerClass = CustomBlockHandler.class,
            blockHandler = "blockHandler",
            fallback = "fb",
            fallbackClass = CustomFallbackClass.class,
            defaultFallback = "dfb")
    @GetMapping("/test1")
    public String test1(String a) {
        // 通过a参数测试程序内部是否产生异常的情况
        if ("1".equals(a))
            throw new RuntimeException("模拟异常");
        return "test1";
    }

    @GetMapping("/test2")
    public String test2() {
        return "test2";
    }

    /**
     * blockHandler熔断降级的处理方法：
     * 方法参数（可在最后增加BlockException参数）和返回值，需要和接口方法保持一致
     * 方法访问范围为public，也可为static方法
     *
     * @param a
     * @param e
     * @return
     */
    public static String bh(String a, BlockException e) {
//        System.out.println(e.getMessage());
        return "SentinelController.hb...";
    }

    /**
     * fallback，参数、返回值与接口的参数、返回值一致，可额外在最后一个参数后面添加Throwable参数
     * 访问范围public，也可为static方法
     * @param a
     * @return
     */
    public static String fb(String a, Throwable t) {
//        System.out.println(t.getMessage());
        return "SentinelController.fb...";
    }

    /**
     * defaultFallback（默认的通用回退方法），不包含参数（Throwable参数外），
     * 以及通用的返回值类型，方法访问范围public，也可为static方法
     * @return
     */
    public String dfb(Throwable t) {
//        System.out.println(t.getMessage());
        return "SentinelController.dfb...";
    }
}
