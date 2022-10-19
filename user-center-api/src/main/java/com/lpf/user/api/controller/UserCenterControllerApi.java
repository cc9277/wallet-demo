package com.lpf.user.api.controller;

import com.lpf.user.api.dto.UserDto;
import com.lpf.user.api.vo.ResponseResult;

import java.util.Map;

public interface UserCenterControllerApi {

    /**
     * 注册
     * @param userDto
     * @return
     */
    public ResponseResult register(UserDto userDto);
    /**
     * 获取验证码
     */
    public  ResponseResult getCode(String phone);

    /**
     * 登录
     */
    public  ResponseResult login(Map param);
}