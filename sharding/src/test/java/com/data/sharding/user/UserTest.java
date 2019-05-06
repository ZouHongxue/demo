package com.data.sharding.user;

import com.data.sharding.mapper.UserMapper;
import com.data.sharding.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void insertUserTest() {
        for (int i = 0; i < 40; i++) {
            User user = new User();
            user.setId(i);
            user.setEmail("hongxuezou@gmail.com"+i);
            user.setPassword("hello_world");
            user.setUserName("邹洪学"+i);
            user.setStatus(true);
            userMapper.insert(user);
        }
    }
}
