package com.example.theyellowriver.gearwenxin.common;

/**
 * 错误码
 *
 * @author Ge Mingjia
 * @date 2023/7/22
 */
public enum ErrorCode {

    PARAMS_ERROR(40000, "请求参数错误"),
    NO_AUTH_ERROR(40101, "无权限"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    OPERATION_ERROR(50001, "操作失败"),
    SYSTEM_NET_ERROR(50002,"系统网络异常");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}