package com.rpc.demo;

import com.alibaba.fastjson.JSON;
import com.rpc.demo.dto.RpcInvokeDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * @ClassName ServiceTask
 * @Description: TODO
 * @Author zly
 * @Date 2021/6/10
 **/
public class ServiceTask implements Runnable{

    private Socket socket;

    public Map<String, Class> serviceRegister;

    public ServiceTask(Socket socket, Map<String, Class> serviceRegister){
        this.socket = socket;
        this.serviceRegister = serviceRegister;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            RpcInvokeDTO dto = (RpcInvokeDTO) ois.readObject();
            System.out.println("接收到客户端调用,入参:" + JSON.toJSONString(dto));
            //获取调用的服务实现类
            Class serverCls = serviceRegister.get(dto.getServiceClass().getName());
            if(serverCls == null){
                System.out.println("找不到调用的服务类");
                return;
            }
            //反射调用方法
            Method method = serverCls.getMethod(dto.getMethodName(), dto.getParamTypes());
            Object result = method.invoke(serverCls.newInstance(), dto.getParamValues());
            //回写执行结果
            oos.writeObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(oos != null){
                    oos.close();
                }
                if(ois != null){
                    ois.close();
                }
                if(socket != null){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
