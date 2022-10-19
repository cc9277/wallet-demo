package com.lpf.user.api.service;

import com.lpf.user.api.dto.UserDto;
import com.lpf.user.api.vo.ResponseResult;

public interface UserCenterService {
    /**
     * 注册用户
     * @param userDto
     * @return
     */
    public ResponseResult register(UserDto userDto);

    /**
     * 用户登录
     * @param phone
     * @param code
     * @return
     */
    public  ResponseResult login(String phone,String code);

    /**
     * 获取验证码
     * @param phone
     * @return
     */
    public ResponseResult getCode(String phone);
}
