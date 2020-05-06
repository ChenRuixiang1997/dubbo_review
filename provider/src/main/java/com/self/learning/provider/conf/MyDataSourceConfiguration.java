package com.self.learning.provider.conf;

import com.google.common.collect.Maps;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @Author: Ruixiang Chen
 * @Date:2020/4/1617:01
 * @Description TODO
 */
@Configuration
public class MyDataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource(){
        return DataSourceBuilder.create().build();
    }

    //数据库路由
    @Bean
    public DataSource dynamicDataSource(){
        Map<Object, Object> dataSouceMap = Maps.newHashMap();
        dataSouceMap.put(MultipleDataSourceHelper.MASTER, masterDataSource());
        dataSouceMap.put(MultipleDataSourceHelper.SLAVE, slaveDataSource());
        DynamicDataSource dds = new DynamicDataSource();
        dds.setTargetDataSources(dataSouceMap);
        dds.setDefaultTargetDataSource(masterDataSource());
        return dds;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        //sqlSessionFactoryBean.setMapperLocations(new path);
        return sqlSessionFactoryBean.getObject();
    }

}
