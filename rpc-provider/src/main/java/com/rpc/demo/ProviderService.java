package com.rpc.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ProviderService
 * @Description: TODO
 * @Author zly
 * @Date 2021/6/10
 **/
public class ProviderService {

    public static Map<String, Class> serviceRegister = new HashMap<>();

    public static final int SERVER_PORT = 9001;

    public static ThreadPoolExecutor executor = null;

    static {
        executor = new ThreadPoolExecutor(5, 10, 300, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(5));
    }

    public void register(Class serviceInterface, Class serviceImpl){
        serviceRegister.put(serviceInterface.getName(), serviceImpl);
    }

    public void start(){
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(SERVER_PORT));
            System.out.println("Bio rpc 服务启动开始###端口###" + SERVER_PORT);
            while (true) {
                executor.execute(new ServiceTask(serverSocket.accept(), serviceRegister));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
