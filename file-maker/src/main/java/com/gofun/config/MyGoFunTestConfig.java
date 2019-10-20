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
@MapperScan(basePackages = "com.gofun.mapper.myGoFunTest", sqlSessionTemplateRef = "myGoFunTestSqlSessionTemplate")
public class MyGoFunTestConfig {

    @Primary
    @Bean(name = "myGoFunTestDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource setDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "myGoFunTestTransactionManager")
    public DataSourceTransactionManager setTransactionManager(@Qualifier("myGoFunTestDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "myGoFunTestSqlSessionFactory")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("myGoFunTestDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/myGoFunTest/*.xml"));
        return bean.getObject();
    }

    @Primary
    @Bean(name = "myGoFunTestSqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("myGoFunTestSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}