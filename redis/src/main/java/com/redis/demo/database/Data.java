package com.redis.demo.database;

import com.redis.demo.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zouhongxue
 * @Date: 2019/4/24 3:00 PM
 */
public class Data {
    public static final Map<Long, User> USER_DATA;

    static {
        USER_DATA = new HashMap<>();
    }

    public static int addUser(User user) {
        try {
            synchronized (USER_DATA) {
                if (USER_DATA.keySet().contains(user.getId())) {
                    return 0;
                } else {
                    USER_DATA.put(user.getId(), user);
                    return 1;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int delUser(Long id) {
        try {
            synchronized (USER_DATA) {
                if (!USER_DATA.keySet().contains(id)) {
                    return 0;
                } else {
                    USER_DATA.remove(id);
                    return 1;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static User findUser(Long id) {
        if (USER_DATA.keySet().contains(id)) {
            return USER_DATA.get(id);
        }
        return null;
    }
}
