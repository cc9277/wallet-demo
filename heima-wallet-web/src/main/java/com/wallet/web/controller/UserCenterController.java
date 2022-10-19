package com.wallet.web.controller;


import com.lpf.user.api.controller.UserCenterControllerApi;
import com.lpf.user.api.dto.UserDto;
import com.lpf.user.api.vo.ResponseResult;
import com.lpf.user.api.service.UserCenterService;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user/v1")
public class UserCenterController implements UserCenterControllerApi {
    @Reference
    private UserCenterService userCenterService;
    @Override
    @PostMapping("/register")
    public ResponseResult register(UserDto userDto) {
        return userCenterService.register(userDto);
    }

    @Override
    @PostMapping("/code")
    public ResponseResult getCode(String phone) {
        return this.userCenterService.getCode(phone);
    }

    @Override
    @PostMapping("/login")
    public ResponseResult login(@RequestBody Map param) {
        String phone= (String) param.get("phone");
        String code= (String) param.get("code");
        return this.userCenterService.login(phone,code);
    }
}
