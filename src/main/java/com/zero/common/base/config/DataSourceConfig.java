package com.zero.common.base.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author yanlv
 * @version 0.1 : DataSourceConfig v0.1 2017/9/16 上午12:02 yanlv Exp $
 */

@Configuration
public class DataSourceConfig {

    @Primary
    @Bean("primaryDataSource")
    @Qualifier("primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDS() {
        return DataSourceBuilder.create().build();
    }


    @Bean("secondaryDataSource")
    @Qualifier("secondaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource secondaryDS() {
        return DataSourceBuilder.create().build();
    }
}
