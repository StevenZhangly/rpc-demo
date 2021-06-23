package com.rpc.demo;

import com.rpc.demo.service.UserService;

/**
 * @ClassName ClientApplition
 * @Description: TODO
 * @Author zly
 * @Date 2021/6/11
 **/
public class ClientApplition {

    public static void main(String[] args) {
        UserService userService = ClientServiceProxy.getProxyService("localhost", 9001, UserService.class);
        userService.findByName("张三");
    }
}
