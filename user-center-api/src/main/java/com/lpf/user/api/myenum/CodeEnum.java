package com.lpf.user.api.myenum;

public enum CodeEnum {
    // 成功段0
    SUCCESS(0,"操作成功"),
    // 登录段1~50
    NEED_LOGIN(1,"需要登录后操作"),
    LOGIN_PASSWORD_ERROR(2,"密码错误"),
    CODE_ERROR(3,"验证码错误或已过期"),
    PHONE_ERROR(4,"手机号不能为空"),
    PHONE_EXIT(5,"手机号已经被注册"),
    // TOKEN50~100
    TOKEN_INVALID(50,"无效的TOKEN"),
    TOKEN_EXPIRE(51,"TOKEN已过期"),
    TOKEN_REQUIRE(52,"TOKEN是必须的"),
    //server 500以上
    PARAM_INVALID(501,"无效参数"),

    SERVER_ERROR(503,"服务器内部错误");
    int code;
    String message;

    CodeEnum(int code, String errorMessage){
        this.code = code;
        this.message = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
