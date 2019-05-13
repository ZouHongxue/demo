package com.data.sharding.user;

import com.data.sharding.mapper.UserMapper;
import com.data.sharding.model.User;
import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Properties;

/**
 * @Author: zouhongxue
 * @Date: 2019/4/29 4:09 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    UserMapper userMapper;

    @Test
    @Transactional
    public void insertUserTest() {

        SnowflakeShardingKeyGenerator snowflakeShardingKeyGenerator = new SnowflakeShardingKeyGenerator();
        snowflakeShardingKeyGenerator.setProperties(new Properties() {{
            // 标识机器ID, 只能为数字  0~1024; 在源码中没有看到 data center ID 配置
            put("worker.id", "0");
        }});

        for (int i = 0; i < 40; i++) {
            User user = new User();
            user.setId((Long) snowflakeShardingKeyGenerator.generateKey());
            System.out.println(user.getId());
            System.out.println(user.getId() % 2);
            user.setEmail("hongxuezou@gmail.com"+i);
            user.setPassword("hello_world");
            user.setUserName("邹洪学"+i);
            user.setStatus(true);
            userMapper.insert(user);
        }
    }
}
