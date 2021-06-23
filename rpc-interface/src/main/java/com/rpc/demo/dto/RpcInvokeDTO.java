package com.rpc.demo.dto;

import java.io.Serializable;

/**
 * @ClassName RpcInvokeDTO
 * @Description: 封装RPC参数
 * @Author zly
 * @Date 2021/6/11
 **/
public class RpcInvokeDTO implements Serializable {

    private Class serviceClass;

    private Class[] paramTypes;

    private Object[] paramValues;

    private String methodName;

    public Class getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(Class serviceClass) {
        this.serviceClass = serviceClass;
    }

    public Class[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getParamValues() {
        return paramValues;
    }

    public void setParamValues(Object[] paramValues) {
        this.paramValues = paramValues;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
