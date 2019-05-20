package com.data.sharding.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: zouhongxue
 * @Date: 2019/4/29 7:52 PM
 */
@MapperScan(value = "com.data.sharding.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
@Configuration
public class DruidConfig {
    /**
     * 加载时读取指定的配置信息,前缀为spring.datasource.druid
     */
    @Bean("ds0")
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DruidDataSource getDruidSourceDs0() {
        return new DruidDataSource();
    }

    @Bean("ds1")
    @ConfigurationProperties(prefix = "spring.datasource.druid.second")
    public DruidDataSource getDruidSourceDs1() {
        return new DruidDataSource();
    }

    @Primary
    @Bean("shardingDataSource")
    public DataSource dataSourceConfiguration(@Qualifier("ds0") DataSource dataSourceDs0,
                                              @Qualifier("ds1") DataSource dataSourceDs1) throws SQLException {
        Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>(4) {{
            put("ds0", dataSourceDs0);
            put("ds1", dataSourceDs1);
        }};
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        shardingRuleConfiguration.setDefaultDatabaseShardingStrategyConfig(
                new InlineShardingStrategyConfiguration("id", "ds${id % 2}"));
        TableRuleConfiguration userTableRuleConfig = new TableRuleConfiguration("user", "ds${0..1}.user");
        shardingRuleConfiguration.getTableRuleConfigs().add(userTableRuleConfig);
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfiguration, new Properties());
    }

    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("shardingDataSource") DataSource shardingDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(shardingDataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/*.xml"));
        return bean.getObject();
    }

    @Bean
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("shardingDataSource") DataSource shardingDataSource) {
        return new DataSourceTransactionManager(shardingDataSource);
    }
}
