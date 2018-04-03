package com.fs.dishes.base.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回结果
 * Created by liuwu on 2018/2/28 0028.
 */
@Data
public class ResResult implements Serializable{

    private static final long serialVersionUID = 1L;

    private int code;

    private String message;

    private Object data;

    public static ResResult ok(){
        ResResult result = new ResResult();
        result.setCode(200);
        return result;
    }

    public static ResResult ok(String message){
        ResResult result = new ResResult();
        result.setCode(200);
        result.setMessage(message);
        return result;
    }

    public static ResResult error(int code,String message){
        ResResult result = new ResResult();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public ResResult withData(Object data){
        this.setData(data);
        return this;
    }
}
