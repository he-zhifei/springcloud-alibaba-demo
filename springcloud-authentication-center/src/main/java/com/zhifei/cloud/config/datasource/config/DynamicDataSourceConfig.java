package com.zhifei.cloud.config.datasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.zhifei.cloud.config.datasource.entity.DynamicRoutingDataSource;
import com.zhifei.cloud.config.datasource.enums.DynamicDataSourceType;
import com.zhifei.cloud.config.datasource.useless.NoWarnTkMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * SpringBoot没有整合DruidDataSource，因此需要个人配置
 * 需要根据实际情况修改的地方：
 *      basePackages：Mapper接口包路径，支持通配符*和**，前者代表1层路径，后者代表任意层（包括0层）
 *      MAPPER_LOCATIONS：mapper.xml文件路径，支持通配符*和**
 *      TYPE_ALIASES_PACKAGE：允许使用别名的实体类，多个用逗号隔开
 * 如需新增数据源，则类似secondaryDataSource那样新增即可，再在dynamicRoutingDataSource()中添加
 *
 * 注意：
 * 1.如果使用的是tk.mapper，则使用的是@tk.mybatis.spring.annotation.MapperScan这个注解，
 * 而不是@org.mybatis.spring.annotation.MapperScan。
 * 2.这里的@org.mybatis.spring.annotation.MapperScan(basePackageClasses = {NoWarnTkMapper.class})，是
 * 处理使用tk.mapper时启动警告信息用的，无实际意义。具体查看{@link NoWarnTkMapper}
 *
 * @author He Zhifei
 * @date 2020/11/22 21:00
 */
@Configuration
@org.mybatis.spring.annotation.MapperScan(basePackageClasses = {NoWarnTkMapper.class})
@tk.mybatis.spring.annotation.MapperScan(basePackages = {"com.zhifei.cloud.dao"},
        sqlSessionFactoryRef = "sqlSessionFactory")
public class DynamicDataSourceConfig {

    /**
     * mapper.xml文件路径
     */
    public static final String MAPPER_LOCATIONS = "mapper/**/*.xml";

    /**
     * 该包下的实体类，在mybatis的xml中可以使用别名
     */
    public static final String TYPE_ALIASES_PACKAGE = "com.zhifei.cloud.entity";

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
        return new DruidDataSource();
    }

    /**
     * 动态数据源
     * @return
     */
    @Bean
    @Primary
    public DynamicRoutingDataSource dynamicRoutingDataSource(
            @Autowired DataSource primaryDataSource
    ) {
        // 需要动态切换的多个数据源
        Map<Object, Object> targetDataSources = new HashMap(2);
        targetDataSources.put(DynamicDataSourceType.PRIMARY.name(), primaryDataSource);

        // 指定不使用@DynamicDataSource注解指定数据源时的默认数据源primaryDataSource
        return new DynamicRoutingDataSource(targetDataSources, primaryDataSource);
    }

    @Bean
    @Primary
    public DataSourceTransactionManager transactionManager(@Autowired DynamicRoutingDataSource dynamicRoutingDataSource) {
        return new DataSourceTransactionManager(dynamicRoutingDataSource);
    }

    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Autowired DynamicRoutingDataSource dynamicRoutingDataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dynamicRoutingDataSource);
        sessionFactory.setTypeAliasesPackage(DynamicDataSourceConfig.TYPE_ALIASES_PACKAGE);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(DynamicDataSourceConfig.MAPPER_LOCATIONS));

        // 因为使用的是druid，所以需要手动配置Mybatis拦截器，默认显示SQL执行时间
//        LogInterceptor logInterceptor = new LogInterceptor();
//        logInterceptor.setProperties(new Properties() {{put(LogInterceptor.SHOW_EXECUTION_TIME, false);}});
//        sessionFactory.setPlugins(logInterceptor);

        return sessionFactory.getObject();
    }
}
