package com.eelex.usercenterbackend.common;


import com.eelex.usercenterbackend.domain.User;

/**
 * 返回工具类
 * @author Eelex
 */
public class ResultUtils {
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok", "ok");
    }
    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

}
