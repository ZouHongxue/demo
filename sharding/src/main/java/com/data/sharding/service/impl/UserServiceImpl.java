package com.data.sharding.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.data.sharding.mapper.UserMapper;
import com.data.sharding.model.User;
import com.data.sharding.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zouhongxue
 * @Date: 2019/4/30 3:17 PM
 */
@Service
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void insertUser(User user) {
        userMapper.insert(user);
        logger.info("新建用户:{}", JSONUtils.toJSONString(user));
    }
}
