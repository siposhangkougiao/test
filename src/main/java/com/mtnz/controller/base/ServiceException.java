package com.mtnz.controller.base;

import java.util.List;
import java.util.Map;

public class ServiceException extends RuntimeException{
    /*
     * 异常代码，用户service返回到controller
     * */
    private int exceptionCode;

    private Map<String,List> data;

    private String key;

    public ServiceException(int exceptionCode, String message, Throwable cause){
        super(message,cause);
        this.exceptionCode=exceptionCode;
    }

    public ServiceException(int exceptionCode, Throwable cause, String key, Map<String,List>data){
        super(cause);
        this.exceptionCode=exceptionCode;
        this.key=key;
        this.data=data;
    }

    public int getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(int exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public Map<String, List> getData() {
        return data;
    }

    public void setData(Map<String, List> data) {
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
