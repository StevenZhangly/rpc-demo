package com.rpc.demo;

import java.lang.reflect.Proxy;

/**
 * @ClassName ClientService
 * @Description: TODO
 * @Author zly
 * @Date 2021/6/11
 **/
public class ClientServiceProxy {

    public static <T> T getProxyService(String ip, int port, Class<T> t){
        return (T) Proxy.newProxyInstance(ClientServiceProxy.class.getClassLoader(), new Class[]{t}, new ClientHandler(
                ip, port, t
        ));
    }
}
