package com.lpf.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.lpf.user.api.pojos.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
}
