package com.redis.demo.controller;


import com.redis.demo.database.Data;
import com.redis.demo.model.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zouhongxue
 * @Date: 2019/4/24 2:53 PM
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 当redis数据没有过期时  插入相同id会插入不进去
     *
     * @param user user
     * @return user
     */
    @RequestMapping("/add")
    @Cacheable(value = "user", key = "#user.id", unless = "#user==null")
    public User addUser(@RequestBody User user) {
        Data.addUser(user);
        return user;
    }

    @RequestMapping("/del/{id}")
    @CacheEvict(value = "user", key = "#id")
    public String delUser(@PathVariable Long id) {
        if (Data.delUser(id) > 0) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping("/get/{id}")
    @Cacheable(value = "user", key = "#id")
    public User findUser(@PathVariable Long id) {
        return Data.findUser(id);
    }
}
