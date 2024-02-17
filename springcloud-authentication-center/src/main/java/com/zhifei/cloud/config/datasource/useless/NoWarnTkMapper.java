package com.zhifei.cloud.config.datasource.useless;

/**
 * tk.mapper整合springboot，启动时会提示：[org.mybatis.spring.mapper.ClassPathMapperScanner] -
 * No MyBatis mapper was found in '[com.zhifei]' package. Please check your configuration.
 * 这个扫描类是mybatis-spring中的，不是tk.mapper中的，但项目中使用的是tk.mapper，因此，需要在配置中添加
 * @org.mybatis.spring.annotation.MapperScan(basePackageClasses = {NoWarnTkMapper.class}) 这个包
 * 下的mapper接口是没任何意义的，只为了让mybatis-spring能扫描到有mapper接口。
 *
 * 注意：当前类所在的路径或者其下的所有路径，不能包含dao的接口，否则启动时又会出现spring已经有了dao实例的异常
 *
 * @author He Zhifei
 * @date 2020/11/25 11:00
 */
public interface NoWarnTkMapper {
}
