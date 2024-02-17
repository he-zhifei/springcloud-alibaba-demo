package com.zhifei.cloud.constant;

public interface LoginConstants {

    /**
     * redis中jwt的key的前缀
     */
    String REDIS_JWT_PREFIX = "security_jwt_";

    /**
     * 认证请求的响应头access_token的请求头名称
     */
    String AUTHORIZATION_NAME = "Authorization";

    /**
     * 认证请求的响应头access_token以Bearer 开头
     */
    String AUTHORIZATION_PREFIX = "Bearer ";

    /**
     * 认证请求的响应头refresh_token的请求头名称
     */
    String REFRESH_TOKEN_NAME = "refreshToken";
}
