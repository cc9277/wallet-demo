package com.lpf.wallet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lpf.wallet.pojo.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
}
