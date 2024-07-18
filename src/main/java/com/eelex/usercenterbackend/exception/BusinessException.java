package com.eelex.usercenterbackend.exception;

import com.eelex.usercenterbackend.common.ErrorCode;

/**
 * 封装全局异常处理器
 * @author Eelex
 */
public class BusinessException extends RuntimeException{
    private final int code;
    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);//要用RuntimeException本身的函数传给他
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());//同上
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());//同上
        this.code = errorCode.getCode();
        this.description = description;
    }

    //getter
    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
