package com.rpc.demo;

import com.rpc.demo.model.User;
import com.rpc.demo.service.UserService;

/**
 * @ClassName UserServiceImpl
 * @Description: TODO
 * @Author zly
 * @Date 2021/6/10
 **/
public class UserServiceImpl implements UserService {


    /**
     * 查询
     *
     * @param name
     * @return
     */
    @Override
    public User findByName(String name) {
        User user = new User();
        user.setName(name);
        user.setAge(1);
        user.setSex(1);
        user.setAddress("abc");
        return user;
    }
}
