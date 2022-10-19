package com.wallet.web.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 验证码
     */
    private  String code;
}
