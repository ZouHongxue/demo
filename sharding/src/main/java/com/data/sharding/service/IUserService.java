package com.data.sharding.service;

import com.data.sharding.model.User;

/**
 * @Author: zouhongxue
 * @Date: 2019/4/30 3:16 PM
 */
public interface IUserService {

    /**
     * 插入用户
     * @param user 用户
     */
    void insertUser(User user);

}
