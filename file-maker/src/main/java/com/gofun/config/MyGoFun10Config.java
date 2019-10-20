package com.gofun.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = "com.gofun.mapper.gofun10", sqlSessionTemplateRef = "gofun10SqlSessionTemplate")
public class MyGoFun10Config {

    @Primary
    @Bean(name = "gofun10DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource setDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "gofun10TransactionManager")
    public DataSourceTransactionManager setTransactionManager(@Qualifier("gofun10DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "gofun10SqlSessionFactory")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("gofun10DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/gofun10/*.xml"));
        return bean.getObject();
    }

    @Primary
    @Bean(name = "gofun10SqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("gofun10SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}