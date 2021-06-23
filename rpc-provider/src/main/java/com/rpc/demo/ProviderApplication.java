package com.rpc.demo;

import com.rpc.demo.service.UserService;

/**
 * @ClassName ProviderApplication
 * @Description: TODO
 * @Author zly
 * @Date 2021/6/10
 **/
public class ProviderApplication {

    public static void main(String[] args) {
        ProviderService providerService = new ProviderService();
        providerService.register(UserService.class, UserServiceImpl.class);
        providerService.start();
    }
}
