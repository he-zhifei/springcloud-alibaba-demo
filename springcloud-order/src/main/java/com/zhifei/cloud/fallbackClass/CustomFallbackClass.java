package com.zhifei.cloud.fallbackClass;

/**
 * 自定义通用的fallback，用法与
 * com.zhifei.springcloud.controller.SentinelController#fb(java.lang.String, java.lang.Throwable)
 * 一致，但必须是static方法
 */
public class CustomFallbackClass {
    public static String fb(String a, Throwable t) {
//        System.out.println(t.getMessage());
        return "CustomFallbackClass.fb...";
    }
}
