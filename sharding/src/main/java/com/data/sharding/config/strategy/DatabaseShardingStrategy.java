package com.data.sharding.config.strategy;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @Author: zouhongxue
 * @Date: 2019/4/30 4:36 PM
 */
public class DatabaseShardingStrategy implements PreciseShardingAlgorithm<Integer> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {
        for (String each : collection) {
            if (each.endsWith(Long.parseLong(preciseShardingValue.getValue().toString()) % 2+"")) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }
}
