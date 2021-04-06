package com.zhx.shardingsphere.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author hongxue.zou @Date 2021-04-06 @Time 14:03
 */
@Configuration
public class ShardingDataSourceConfig {

  @Bean
  DataSource dataSource () throws SQLException {
    // 配置真实数据源
    Map<String, DataSource> dataSourceMap = new HashMap<>();

    // 配置第 1 个数据源
    HikariDataSource dataSource1 = new HikariDataSource();
    dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
    dataSource1.setJdbcUrl("jdbc:mysql://localhost:3306/ds0?useSSL=false");
    dataSource1.setUsername("root");
    dataSource1.setPassword("password");
    dataSourceMap.put("ds0", dataSource1);

// 配置第 2 个数据源
    HikariDataSource dataSource2 = new HikariDataSource();
    dataSource2.setDriverClassName("com.mysql.jdbc.Driver");
    dataSource2.setJdbcUrl("jdbc:mysql://localhost:3306/ds1?useSSL=false");
    dataSource2.setUsername("root");
    dataSource2.setPassword("password");
    dataSourceMap.put("ds1", dataSource2);

// 配置 t_order 表规则
    ShardingTableRuleConfiguration orderTableRuleConfig =
        new ShardingTableRuleConfiguration("t_order", "ds${0..1}.t_order${0..1}");

// 配置分库策略
    orderTableRuleConfig.setDatabaseShardingStrategy(
        new StandardShardingStrategyConfiguration("id", "dbShardingAlgorithm"));

// 配置分表策略
    orderTableRuleConfig.setTableShardingStrategy(
        new StandardShardingStrategyConfiguration("id", "tableShardingAlgorithm"));

// 省略配置 t_order_item 表规则...
// ...

// 配置分片规则
    ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
    shardingRuleConfig.getTables().add(orderTableRuleConfig);

// 配置分库算法
    Properties dbShardingAlgorithmrProps = new Properties();
    dbShardingAlgorithmrProps.setProperty("algorithm-expression", "ds${id % 2}");
    shardingRuleConfig.getShardingAlgorithms()
        .put("dbShardingAlgorithm", new ShardingSphereAlgorithmConfiguration("INLINE", dbShardingAlgorithmrProps));

// 配置分表算法
    Properties tableShardingAlgorithmrProps = new Properties();
    tableShardingAlgorithmrProps.setProperty("algorithm-expression", "t_order${id % 2}");
    shardingRuleConfig.getShardingAlgorithms().put("tableShardingAlgorithm", new ShardingSphereAlgorithmConfiguration("INLINE", tableShardingAlgorithmrProps));

// 创建 ShardingSphereDataSource
    return ShardingSphereDataSourceFactory.createDataSource(
        dataSourceMap, Collections.singleton(shardingRuleConfig), new Properties());
  }
}
