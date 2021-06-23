package com.rpc.demo.service;

import com.rpc.demo.model.User;

/**
 * @ClassName UserService
 * @Description: TODO
 * @Author zly
 * @Date 2021/6/10
 **/
public interface UserService {

    /**
     * 查询
     * @param name
     * @return
     */
    public User findByName(String name);
}
