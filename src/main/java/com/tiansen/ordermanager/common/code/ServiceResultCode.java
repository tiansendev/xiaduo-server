package com.tiansen.ordermanager.common.code;

/**
 * 全局错误码
 */
public enum ServiceResultCode {

    /**
     * 通用
     */
    SUCCESS("200000", "success"),
    PERMISSION_NO_ENOUGH("200001", "权限不足"),
    PARAM_EXCEPTION("200002", "参数异常"),
    RESOURCE_UNFIND("200004", "找不到对应资源"),
    TOKEN_EXPIRE("2000A0", "未登录或token过期"),


    SYSTEM_EXCEPTION("2000F0", "服务器异常"),

    /**
     * 用户相关
     */
    USER_PASSWORD_ERR("301101", "用户名或密码错误"),
    USER_HAD_EXIST("301104", "用户已经存在"),
    ;

    private String code;
    private String message;

    private ServiceResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ServiceResultCode getByCode(String code) {
        for (ServiceResultCode c : ServiceResultCode.values()) {
            if (c.code.equalsIgnoreCase(code)) {
                return c;
            }
        }
        return null;
    }

    public static String getNameByCode(String code) {
        for (ServiceResultCode c : ServiceResultCode.values()) {
            if (c.code.equalsIgnoreCase(code)) {
                return c.message;
            }
        }
        return null;
    }
}
