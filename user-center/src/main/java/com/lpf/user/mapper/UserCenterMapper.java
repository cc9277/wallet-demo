package com.lpf.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lpf.user.api.pojos.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserCenterMapper extends BaseMapper<User> {
}
