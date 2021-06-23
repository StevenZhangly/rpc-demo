package com.rpc.demo;

import com.alibaba.fastjson.JSON;
import com.rpc.demo.dto.RpcInvokeDTO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @ClassName ClientHandler
 * @Description: TODO
 * @Author zly
 * @Date 2021/6/11
 **/
public class ClientHandler implements InvocationHandler {

    private String ip;

    private int port;

    private Class serviceClass;

    public ClientHandler(String ip, int port, Class serviceClass){
        this.ip = ip;
        this.port = port;
        this.serviceClass = serviceClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        Socket socket = null;
        try {
            socket = new Socket(ip, port);
            RpcInvokeDTO dto = buildRpcInvoke(method.getName(), args);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            oos.writeObject(dto);
            oos.flush();
            Object result = ois.readObject();
            System.out.println("接收到服务端响应, 出参:" + JSON.toJSONString(result));
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ois != null){
                    ois.close();
                }
                if(oos != null){
                    oos.close();
                }
                if(socket != null){
                    socket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public RpcInvokeDTO buildRpcInvoke(String methodName, Object[] args){
        RpcInvokeDTO dto = new RpcInvokeDTO();
        dto.setServiceClass(serviceClass);
        dto.setMethodName(methodName);
        Class[] paramTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            paramTypes[i] = args[i].getClass();
        }
        dto.setParamTypes(paramTypes);
        dto.setParamValues(args);
        return dto;
    }
}
