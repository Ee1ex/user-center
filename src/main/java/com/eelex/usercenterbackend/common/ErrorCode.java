package com.eelex.usercenterbackend.common;

/**
 * 错误码
 * @create 2023/4/1 16:09
 * @author Eelex
 */
public enum ErrorCode {
    /**
     * 状态码
     */
    SUCCESS(0, "ok", "成功"),
    PARAMS_ERROR(40000, "请求参数错误", "请求参数错误"),
    NULL_ERROR(40001, "请求数据为空", "请求数据为空"),
    NOT_LOGIN(40100, "未登录", "未登录"),
    NO_AUTH(40101, "无权限", "无权限"),
    SYSTEM_ERROR(50000, "系统内部异常", "系统内部异常"),
    OPERATION_ERROR(50001, "操作失败", "操作失败");



    private final int code;//错误码
    private final String message;//错误信息
    private final String description;//错误描述

    /**
     * 构造函数
     * @param code
     * @param message
     * @param description
     */
    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

}
