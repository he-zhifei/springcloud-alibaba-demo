package com.zhifei.cloud.plugin.exception.enums;

/**
 * 异常码（只需新增异常信息，抛出自定义的通用异常即可）
 *
 * @author He Zhifei
 * @date 2020/6/6 13:45
 */
public enum RCode {

    /**
     * 成功响应码
     */
    SUCCESS(10000, "操作成功"),

    /**
     * 失败响应码
     */
    FAILED(-10000, "操作失败"),

    /**
     * 参数校验失败响应码
     */
    VALIDATE_FAILED(-10001, "参数校验失败"),

    /**
     * 缺失请求体响应码
     */
    MISSING_REQUEST_BODY(-10002, "缺失请求体"),

    /**
     * 重新登录响应码
     */
    NEED_RELOGIN(-20000, "请重新登录"),

    /**
     * 重新获取access_token响应码
     */
    NEED_REFRESH_TOKEN(-20001, "请重新获取access_token"),

    /**
     * 未知错误响应码
     */
    ERROR(-50000, "未知错误"),

    /**
     * 权限不足响应码
     */
    UNAUTHORIZED(-51000, "权限不足"),

    /**
     * 密码不正确响应码
     */
    INCORRECT_CREDENTIALS(-51001, "密码不正确");

    private int code;

    private String message;

    RCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}