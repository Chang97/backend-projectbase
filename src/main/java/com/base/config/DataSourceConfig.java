package com.base.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.common")
    public HikariConfig commonConfig() {
        return new HikariConfig();
    }

    @Bean
    public DataSource dataSource(HikariConfig commonConfig) {
        return new HikariDataSource(commonConfig);
    }
}
