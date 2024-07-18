package com.eelex.usercenterbackend.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @param <T>
 * @author Eelex
 * 通用返回结果，服务端响应的数据最终都会封装成这个类
 */
@Data
//通用返回对象，统一处理异常
public class BaseResponse<T> implements Serializable{
    private static final long serialVersionUID = 1L;

    private int code;
    private T data;
    private String message;
    private String description;


    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }
    public BaseResponse(int code, T data) {
        this.code = code;
        this.data = data;
        this.message = "";
        this.description = "";
    }



    public BaseResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.description = errorCode.getDescription();
    }
    public BaseResponse(ErrorCode errorCode, String message, String description) {
        this.code = errorCode.getCode();
        this.message = message;
        this.description = description;
    }
    public BaseResponse(ErrorCode errorCode, String message) {
        this.code = errorCode.getCode();
        this.message = message;
        this.description = errorCode.getDescription();
    }
    public BaseResponse(ErrorCode errorCode, T data) {
        this.code = errorCode.getCode();
        this.data = data;
        this.message = errorCode.getMessage();
        this.description = errorCode.getDescription();
    }



}
