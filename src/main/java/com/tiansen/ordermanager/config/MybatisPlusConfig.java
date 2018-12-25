package com.tiansen.ordermanager.config;


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisMapperRefresh;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.tiansen.ordermanager.mybatis.mapper")
public class MybatisPlusConfig {
    @Bean
    @ConfigurationProperties("spring.datasource" )
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        mybatisSqlSessionFactoryBean.setDataSource(dataSource());

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        mybatisSqlSessionFactoryBean.setConfiguration(configuration);
        mybatisSqlSessionFactoryBean.setTypeHandlersPackage("com.tiansen.ordermanager.mybatis.handler");
        mybatisSqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*Mapper.xml"));

        mybatisSqlSessionFactoryBean.setPlugins(new Interceptor[]{
                performanceInterceptor(),
                paginationInterceptor()
        });
        SqlSessionFactory sqlSessionFactory=mybatisSqlSessionFactoryBean.getObject();
        return sqlSessionFactory;
    }

    @Bean
    public MybatisMapperRefresh mybatisMapperRefresh() throws Exception {
        return new MybatisMapperRefresh(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*Mapper.xml"),
                sqlSessionFactory(),
                5, 10, true);
    }

//    @Bean("sqlSessionFactory")
//    @ConfigurationProperties("spring.datasource" )
//    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
//        sqlSessionFactory.setDataSource(dataSource);
//        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*Mapper.xml"));
//        MybatisConfiguration configuration = new MybatisConfiguration();
//        configuration.setJdbcTypeForNull(JdbcType.NULL);
//        configuration.setMapUnderscoreToCamelCase(true);
//        configuration.setCacheEnabled(false);
//
//        sqlSessionFactory.setConfiguration(configuration);
////        sqlSessionFactory.setGlobalConfig(globalConfiguration());
//        sqlSessionFactory.setPlugins(new Interceptor[]{
//                performanceInterceptor(),
//                paginationInterceptor()
//        });
//        return sqlSessionFactory.getObject();
//    }
    // 配置事物管理器
    @Bean(name="transactionManager")
    public DataSourceTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
    /**
     * 性能分析拦截器，不建议生产使用
     * 用来观察 SQL 执行情况及执行时长
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor(){
        return new PerformanceInterceptor();
    }
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}